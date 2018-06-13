package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.TitreDao;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Titre;


public class TitreDaoImpl extends DaoImpl implements TitreDao {
    @Override
    public Titre get(Integer id) {
        Titre result = realm.where(Titre.class)
                .equalTo("id_titres",id)
                .findFirst();
        if (result != null){
            return realm.copyFromRealm(result);
        }

        return null;
    }

    @Override
    public List<Titre> list() {
        return realm.copyFromRealm(realm.where(Titre.class).findAll()) ;
    }

    @Override
    public void delete(Integer id) {
        realm.beginTransaction();
        Titre result = realm.where(Titre.class)
                .equalTo("id_titres",id)
                .findFirst();
        if (result != null){
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    @Override
    public Titre write(Titre obj) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
        return obj;
    }

    @Override
    public void deleteByGroup(int id_groupes) {
        realm.beginTransaction();
        realm.where(Titre.class)
                .equalTo("id_groupes",id_groupes)
                .findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }
}
