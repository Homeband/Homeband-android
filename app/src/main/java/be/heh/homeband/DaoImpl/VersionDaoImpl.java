package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.VersionDao;
import be.heh.homeband.entities.Version;
import io.realm.RealmQuery;

public class VersionDaoImpl extends DaoImpl implements VersionDao {

    @Override
    public Version getByNomTable(String nomtable) {
        RealmQuery<Version> query = realm.where(Version.class);
        query.equalTo("nom_table", "VILLES");
        return query.findFirst();
    }

    @Override
    public Version get(Integer id) {
        return null;
    }

    @Override
    public List<Version> list() {
        List<Version> listeVersionsDB = realm.copyFromRealm(realm.where(Version.class).findAll());
        return listeVersionsDB;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Version write(Version obj) {
        return realm.copyToRealmOrUpdate(obj);
    }
}
