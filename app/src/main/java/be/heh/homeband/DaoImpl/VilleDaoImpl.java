package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.VilleDao;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Utilisateur;
import be.heh.homeband.entities.Ville;

public class VilleDaoImpl extends DaoImpl implements VilleDao {
    @Override
    public Ville get(Integer id) {
       return realm.where(Ville.class).equalTo("id_villes",id).findFirst();
    }

    @Override
    public List<Ville> list() {
        return realm.copyFromRealm(realm.where(Ville.class).findAll()) ;
    }

    @Override
    public void delete(Integer id) {
        realm.beginTransaction();
        Ville result = realm.where(Ville.class)
                .equalTo("id_villes",id)
                .findFirst();
        if (result != null){
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    @Override
    public Ville write(Ville obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        return obj;
    }
}
