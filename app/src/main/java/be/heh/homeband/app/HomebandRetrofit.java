package be.heh.homeband.app;


import com.google.common.hash.Hashing;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.DaoImpl.UtilisateurDaoImpl;
import be.heh.homeband.entities.Utilisateur;

/**
 * Created on 10/02/2018.
 */

public class HomebandRetrofit  {
    public static String API_URL ="http://10.0.2.2/homeband-api/" ;
    //public static String API_URL ="http://dev.zen-project.be/homeband-api/" ;

    private static String APP_KEY = "zXcD3WS21G0300mqxNaecHvnmy37W4rw";
    private static String SECRET_KEY = "LcBaMofTEoPqBHPwqOJHHOPQ0n9vP6cGxf2PnpbNQW3gELs";

    private static String HEADER_AK = "X-Homeband-AK";
    private static String HEADER_TS = "X-Homeband-TS";
    private static String HEADER_CK = "X-Homeband-CK";
    private static String HEADER_SIGN = "X-Homeband-SIGN";

    public HomebandRetrofit() {

    }

    public static Map<String, String> headers() {
        Map<String, String> headers = new HashMap<String, String>();
        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl();
        Utilisateur user = utilisateurDao.getConnectedUser();

        String ak = APP_KEY;
        String as = SECRET_KEY;
        String ck = "";
        String ts = String.valueOf(new Timestamp(System.currentTimeMillis() / 1000).getTime());
        String toSign = "";
        String sign = "";


        // Vérification de l'utilisateur
        if(user != null){
            ck = user.getApi_ck();
            headers.put(HEADER_CK, ck);
        }

        // Clé d'application
        headers.put(HEADER_AK, ak);

        // Timestamp
        headers.put(HEADER_TS, ts);

        // Construction de la signature
        toSign = as + "+" + ck + "+" + ts;
        sign = "$1$" + Hashing.sha256().hashString(toSign, StandardCharsets.ISO_8859_1).toString();
        headers.put(HEADER_SIGN, sign);

        return headers;
    }
}
