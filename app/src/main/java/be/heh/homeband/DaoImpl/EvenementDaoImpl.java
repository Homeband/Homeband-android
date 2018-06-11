package be.heh.homeband.DaoImpl;

import java.util.ArrayList;
import java.util.List;

import be.heh.homeband.Dao.EvenementDao;
import be.heh.homeband.entities.Evenement;

public class EvenementDaoImpl extends DaoImpl implements EvenementDao {
    @Override
    public Evenement get(Integer id) {
        return realm.where(Evenement.class).equalTo("id_evenements",id).findFirst();
    }

    @Override
    public List<Evenement> list() {
        return realm.copyFromRealm(realm.where(Evenement.class).findAll()) ;
    }

    @Override
    public void delete(Integer id) {
        realm.beginTransaction();
        Evenement result = realm.where(Evenement.class)
                .equalTo("id_evenements",id)
                .findFirst();
        if (result != null){
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    @Override
    public Evenement write(Evenement obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        return obj;
    }

    @Override
    public List<Evenement> listByGroup(int id_groupes) {
        return realm.where(Evenement.class)
                .equalTo("id_groupes",id_groupes)
                .findAll();
    }

    @Override
    public List<Evenement> listByUser(int id_utilisateurs) {
        List<Evenement> evenements = realm.where(Evenement.class).equalTo("users.id_utilisateurs", id_utilisateurs).findAll();
        if(evenements.size() > 0){
            return realm.copyFromRealm(evenements);
        }

        return new ArrayList<Evenement>();
    }

    @Override
    public boolean isUsed(int id_evenements) {
        Evenement evenementDB = realm.where(Evenement.class).equalTo("id_evenements", id_evenements).findFirst();
        if(evenementDB != null){
            return !(evenementDB.getUsers().isEmpty());
        }

        return false;
    }
}
