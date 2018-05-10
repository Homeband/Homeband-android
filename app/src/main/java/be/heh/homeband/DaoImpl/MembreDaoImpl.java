package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.MembreDao;
import be.heh.homeband.entities.Membre;
import io.realm.RealmQuery;

public class MembreDaoImpl extends DaoImpl implements MembreDao {
    @Override
    public Membre get(Integer id) {
        return null;
    }

    @Override
    public List<Membre> list() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Membre write(Membre obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        return obj;
    }

    @Override
    public List<Membre> getByGroupe(int id_groupe) {
        RealmQuery<Membre> query = realm.where(Membre.class);
        return query.equalTo("id_groupes", id_groupe).findAll();
    }
}
