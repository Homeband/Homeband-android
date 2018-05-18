package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.TitreDao;
import be.heh.homeband.entities.Titre;


public class TitreDaoImpl extends DaoImpl implements TitreDao {
    @Override
    public Titre get(Integer id) {
        return null;
    }

    @Override
    public List<Titre> list() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Titre write(Titre obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        return obj;
    }
}
