package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.UtilisateurDao;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Utilisateur;
import io.realm.Realm;
import io.realm.RealmQuery;

public class UtilisateurDaoImpl extends DaoImpl implements UtilisateurDao {
    @Override
    public Utilisateur get(Integer id) {
        Utilisateur user = realm.where(Utilisateur.class).equalTo("id_utilisateurs",id).findFirst();
        if(user != null){
            return realm.copyFromRealm(user);
        }
        return null;
    }

    @Override
    public List<Utilisateur> list() {
        return realm.copyFromRealm(realm.where(Utilisateur.class).findAll()) ;
    }

    @Override
    public void delete(Integer id) {
        realm.beginTransaction();
        Utilisateur result = realm.where(Utilisateur.class)
                .equalTo("id_utilisateurs",id)
                .findFirst();
        if (result != null){
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    @Override
    public Utilisateur write(Utilisateur obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        return obj;
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

    @Override
    public void addGroup(int id_utilisateurs, Groupe groupe) {
        if(id_utilisateurs > 0 && groupe != null){
            Utilisateur user = realm.where(Utilisateur.class).equalTo("id_utilisateurs", id_utilisateurs).findFirst();
            if(user != null){
                Groupe groupeDB = realm.where(Groupe.class).equalTo("id_groupes", groupe.getId_groupes()).findFirst();
                if(groupeDB == null){
                    groupeDB = groupe;
                }

                realm.beginTransaction();
                user.getGroups().add(groupeDB);
                realm.commitTransaction();
            }
        }
    }

    @Override
    public void deleteGroup(int id_utilisateurs, int id_groupes) {
        if(id_utilisateurs > 0 && id_groupes > 0){
            Utilisateur user = realm.where(Utilisateur.class).equalTo("id_utilisateurs", id_utilisateurs).findFirst();
            if(user != null){
                Groupe groupeDB = realm.where(Groupe.class).equalTo("id_groupes", id_groupes).findFirst();
                if(groupeDB != null){
                    realm.beginTransaction();
                    user.getGroups().remove(groupeDB);
                    if(groupeDB.getUsers().size() == 0 && groupeDB.getEvents().size() == 0){
                        groupeDB.deleteFromRealm();
                    }
                    realm.commitTransaction();
                }
            }
        }
    }

    @Override
    public Groupe getGroup(int id_utilisateurs, int id_groupes) {
        if(id_utilisateurs > 0 && id_groupes > 0){
            Utilisateur user = realm.where(Utilisateur.class).equalTo("id_utilisateurs", id_utilisateurs).findFirst();
            if(user != null){
                Groupe groupeDB = realm.where(Groupe.class)
                        .equalTo("id_groupes", id_groupes)
                        .equalTo("users.id_utilisateurs", user.getId_utilisateurs())
                        .findFirst();
                if(groupeDB != null){
                    return realm.copyFromRealm(groupeDB);
                }
            }
        }

        return null;
    }

    @Override
    public void addEvent(int id_utilisateurs, Evenement evenement) {
        if(id_utilisateurs > 0 && evenement != null){
            Utilisateur user = realm.where(Utilisateur.class).equalTo("id_utilisateurs", id_utilisateurs).findFirst();
            if(user != null){
                Evenement evenementDB = realm.where(Evenement.class).equalTo("id_evenements", evenement.getId_evenements()).findFirst();
                if(evenementDB == null){
                    evenementDB = evenement;
                }

                realm.beginTransaction();
                user.getEvents().add(evenementDB);
                realm.commitTransaction();
            }
        }
    }

    @Override
    public void deleteEvent(int id_utilisateurs, int id_evenements) {
        if(id_utilisateurs > 0 && id_evenements > 0){
            Utilisateur user = realm.where(Utilisateur.class).equalTo("id_utilisateurs", id_utilisateurs).findFirst();
            if(user != null){
                Evenement evenementDB = realm.where(Evenement.class).equalTo("id_evenements", id_evenements).findFirst();
                if(evenementDB != null){
                    realm.beginTransaction();
                    user.getEvents().remove(evenementDB);
                    if(evenementDB.getUsers().size() == 0){
                        evenementDB.deleteFromRealm();
                    }
                    realm.commitTransaction();
                }
            }
        }
    }

    @Override
    public Evenement getEvent(int id_utilisateurs, int id_evenements) {
        if(id_utilisateurs > 0 && id_evenements > 0){
            Utilisateur user = realm.where(Utilisateur.class).equalTo("id_utilisateurs", id_utilisateurs).findFirst();
            if(user != null){
                Evenement evenementDB = realm.where(Evenement.class)
                        .equalTo("id_evenements", id_evenements)
                        .equalTo("users.id_utilisateurs", user.getId_utilisateurs())
                        .findFirst();
                if(evenementDB != null){
                    return realm.copyFromRealm(evenementDB);
                }
            }
        }

        return null;
    }
}
