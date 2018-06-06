package be.heh.homeband.DaoImpl;

import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.Dao.GroupeDao;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;

public class GroupeDaoImpl extends DaoImpl implements GroupeDao {
    @Override
    public Groupe get(Integer id) {
        return realm.where(Groupe.class).equalTo("id_groupes",id).findFirst();
    }

    @Override
    public List<Groupe> list() {
        return realm.copyFromRealm(realm.where(Groupe.class).findAll()) ;
    }

    @Override
    public void delete(Integer id) {
        realm.beginTransaction();
        Groupe result = realm.where(Groupe.class)
                .equalTo("id_groupes",id)
                .findFirst();
        if (result != null){
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    @Override
    public Groupe write(Groupe obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        return obj;
    }

    @Override
    public List<Groupe> listByUser(int id_utilisateurs) {
        List<Groupe> groupes = realm.where(Groupe.class).equalTo("users.id_utilisateurs", id_utilisateurs).findAll();
        if(groupes.size() > 0){
            return realm.copyFromRealm(groupes);
        }

        return new ArrayList<Groupe>();
    }

    @Override
    public boolean isUsed(int id_groupes) {
        Groupe groupeDB = realm.where(Groupe.class).equalTo("id_groupes", id_groupes).findFirst();
        if(groupeDB != null){
            return !(groupeDB.getEvents().isEmpty() || groupeDB.getUsers().isEmpty());
        }

        return false;
    }
}
