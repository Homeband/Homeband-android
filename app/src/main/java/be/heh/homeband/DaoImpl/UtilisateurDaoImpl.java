package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Utilisateur;
import io.realm.Realm;
import io.realm.RealmQuery;

public class UtilisateurDaoImpl extends DaoImpl implements UtilisateurDao {
    @Override
    public Utilisateur get(Integer id) {
        return realm.where(Utilisateur.class).equalTo("id_utilisateurs",id).findFirst();
    }

    @Override
    public List<Utilisateur> list() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Utilisateur write(Utilisateur obj) {
        return null;
    }

    @Override
    public Utilisateur getConnectedUser() {
        RealmQuery<Utilisateur> query = realm.where(Utilisateur.class);
        query.equalTo("est_connecte", true);

        Utilisateur user = query.findFirst();
        if(user != null){
            return realm.copyFromRealm(query.findFirst());
        } else {
            return null;
        }
    }

    @Override
    public void disconnectUser() {
        final Utilisateur user = getConnectedUser();
        if (user != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    user.setEst_connecte(false);
                    realm.copyToRealmOrUpdate(user);
                }
            });
        }
    }
}
