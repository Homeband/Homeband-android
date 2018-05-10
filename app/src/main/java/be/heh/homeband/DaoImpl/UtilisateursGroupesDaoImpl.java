package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.UtilisateursGroupesDao;
import be.heh.homeband.entities.UtilisateursGroupes;

public class UtilisateursGroupesDaoImpl extends DaoImpl implements UtilisateursGroupesDao {
    @Override
    public UtilisateursGroupes get(Integer id) {
        return null;
    }

    @Override
    public List<UtilisateursGroupes> list() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public UtilisateursGroupes write(UtilisateursGroupes obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        return obj;
    }
}
