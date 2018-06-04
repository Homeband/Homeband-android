package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.AlbumDao;
import be.heh.homeband.entities.Album;

public class AlbumDaoImpl extends DaoImpl implements AlbumDao {
    @Override
    public Album get(Integer id) {
        return null;
    }

    @Override
    public List<Album> list() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Album write(Album obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        return obj;
    }

    @Override
    public void deleteByGroup(int id_groupes) {
        realm.beginTransaction();
        realm.where(Album.class)
                .equalTo("id_groupes",id_groupes)
                .findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }
}
