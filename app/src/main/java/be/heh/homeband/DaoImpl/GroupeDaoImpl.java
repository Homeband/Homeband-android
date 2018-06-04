package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.GroupeDao;
import be.heh.homeband.entities.Groupe;

public class GroupeDaoImpl extends DaoImpl implements GroupeDao {
    @Override
    public Groupe get(Integer id) {
        return realm.where(Groupe.class).equalTo("id_groupes",id).findFirst();
    }

    @Override
    public List<Groupe> list() {
        return null;
    }

    @Override
    public void delete(Integer id) {
        realm.where(Groupe.class)
                .equalTo("id_groupes",id)
                .findAll().deleteAllFromRealm();
    }

    @Override
    public Groupe write(Groupe obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        return obj;
    }
}
