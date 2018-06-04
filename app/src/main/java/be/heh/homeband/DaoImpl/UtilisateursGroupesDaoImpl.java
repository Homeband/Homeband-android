package be.heh.homeband.DaoImpl;

import java.util.HashMap;
import java.util.List;

import be.heh.homeband.Dao.UtilisateursGroupesDao;
import be.heh.homeband.entities.UtilisateursGroupes;

public class UtilisateursGroupesDaoImpl extends DaoImpl implements UtilisateursGroupesDao {

    public static String KEY_UTILISATEUR = "id_utilisateurs";
    public static String KEY_GROUPE = "id_groupes";

    @Override
    public UtilisateursGroupes get(HashMap<String, Integer> id) {
        if(id.containsKey(KEY_UTILISATEUR) && id.containsKey(KEY_GROUPE)) {
            int id_utilisateur = id.get(KEY_UTILISATEUR);
            int id_groupe = id.get(KEY_GROUPE);
            UtilisateursGroupes liaison = realm.where(UtilisateursGroupes.class)
                    .equalTo("id_groupes",id_groupe)
                    .equalTo("id_utilisateurs",id_utilisateur)
                    .findFirst();

            if(liaison != null){
                return realm.copyFromRealm(liaison);
            }

        }

        return null;
    }

    @Override
    public List<UtilisateursGroupes> list() {
        return null;
    }

    @Override
    public void delete(HashMap<String, Integer> id) {
        realm.beginTransaction();
        if(id.containsKey(KEY_UTILISATEUR) && id.containsKey(KEY_GROUPE)) {
            int id_utilisateur = id.get(KEY_UTILISATEUR);
            int id_groupe = id.get(KEY_GROUPE);
            realm.where(UtilisateursGroupes.class)
                    .equalTo("id_groupes",id_groupe)
                    .equalTo("id_utilisateurs",id_utilisateur)
                    .findAll().deleteAllFromRealm();

        }
        realm.commitTransaction();
    }

    @Override
    public UtilisateursGroupes write(UtilisateursGroupes obj) {
        realm.beginTransaction();
        realm.copyToRealm(obj);
        realm.commitTransaction();
        return obj;
    }
}
