package be.heh.homeband.app;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import be.heh.homeband.R;
import be.heh.homeband.entities.Style;
import be.heh.homeband.entities.Utilisateur;
import be.heh.homeband.entities.Version;
import be.heh.homeband.entities.Ville;
import io.realm.Realm;
import io.realm.RealmQuery;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by christopher on 07/03/2018.
 */

public abstract class HomebandTools {

    public static Utilisateur getConnectedUser() {
        Realm realm = Realm.getDefaultInstance();
        // Build the query looking at all users:
        RealmQuery<Utilisateur> query = realm.where(Utilisateur.class);

        // Add query conditions:
        query.equalTo("est_connecte", true);

        // Execute the query:
        Utilisateur user = query.findFirst();
        return user;
    }

    public static void disconnectUser() {
        final Utilisateur user = getConnectedUser();
        if (user != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    user.setEst_connecte(false);
                    realm.copyToRealmOrUpdate(user);
                }
            });
        }
    }

    public static boolean checkUpdateVille(Context context){
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            // Requête vers l'API
            Call<HomebandApiReponse> call = serviceApi.getVersions("villes");
            HomebandApiReponse response = call.execute().body();
            Gson gson = new Gson();
            final Version versionAPI = gson.fromJson(response.get("version").getAsJsonObject(), Version.class);

            //Requête vers base de donnée interne
            Realm realm = Realm.getDefaultInstance();
            RealmQuery<Version> query = realm.where(Version.class);
            query.equalTo("nom_table", "VILLES");
            Version versionDB = query.findFirst();

            if (versionDB == null)
            {
                return true;
            }
            else{
                if ( versionAPI.getDate_maj().after(versionDB.getDate_maj()) ){
                    return true;
                }
               return false;
            }


        } catch (Exception e){
            Toast.makeText(context,(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }

    public static void updateVilles(Context context){
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HomebandRetrofit.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Création d'une instance du service avec Retrofit
            HomebandApiInterface serviceApi = retrofit.create(HomebandApiInterface.class);

            // Requête vers l'API
            Call<HomebandApiReponse> call = serviceApi.getVilles();
            HomebandApiReponse response = call.execute().body();
            Gson gson = new Gson();
            Type typeListe = new TypeToken<List<Ville>>(){}.getType();
            final List<Ville> villes = gson.fromJson(response.get("villes").getAsJsonArray(), typeListe);

            //Requête vers base de donnée interne
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    for (Iterator<Ville> i = villes.iterator(); i.hasNext();) {
                        Ville item = i.next();
                        realm.copyToRealmOrUpdate(item);
                    }

                }
            });
            RealmQuery<Version> query = realm.where(Version.class);
            query.equalTo("nom_table", "VILLES");
            Version versionDB = query.findFirst();
            if (versionDB==null){
                versionDB = new Version() ;
                versionDB.setNom_table("VILLES");
                versionDB.setNum_table(2);
            }
            versionDB.setDate_maj(new Date());

        } catch (Exception e){
            Toast.makeText(context,(CharSequence)"Exception",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
