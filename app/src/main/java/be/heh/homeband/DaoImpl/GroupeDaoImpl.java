package be.heh.homeband.DaoImpl;

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
}
