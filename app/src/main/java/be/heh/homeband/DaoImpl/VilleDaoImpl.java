package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.Dao;
import be.heh.homeband.Dao.VersionDao;
import be.heh.homeband.Dao.VilleDao;
import be.heh.homeband.entities.Version;
import be.heh.homeband.entities.Ville;

public class VilleDaoImpl extends DaoImpl implements VilleDao {
    @Override
    public Ville get(Integer id) {
       return realm.where(Ville.class).equalTo("id_villes",id).findFirst();
    }

    @Override
    public List<Ville> list() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Ville write(Ville obj) {
        return null;
    }
}
