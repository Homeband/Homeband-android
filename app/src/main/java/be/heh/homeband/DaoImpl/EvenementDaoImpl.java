package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.EvenementDao;
import be.heh.homeband.entities.Evenement;

public class EvenementDaoImpl extends DaoImpl implements EvenementDao {
    @Override
    public Evenement get(Integer id) {
        return null;
    }

    @Override
    public List<Evenement> list() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Evenement write(Evenement obj) {
        return null;
    }

    @Override
    public List<Evenement> listByGroup(int id_groupes) {
        return realm.where(Evenement.class)
                .equalTo("id_groupes",id_groupes)
                .findAll();
    }

    @Override
    public void deleteByGroup(int id_groupes) {
        realm.where(Evenement.class)
                .equalTo("id_groupes",id_groupes)
                .findAll().deleteAllFromRealm();
    }
}
