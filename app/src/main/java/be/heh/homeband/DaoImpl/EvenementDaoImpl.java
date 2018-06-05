package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.EvenementDao;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;

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
    public void deleteByGroup(int id_groupes) {
        realm.where(Evenement.class)
                .equalTo("id_groupes",id_groupes)
                .findAll().deleteAllFromRealm();
    }
}
