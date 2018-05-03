package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.AdresseDao;
import be.heh.homeband.entities.Adresse;

public class AdresseDaoImpl  extends DaoImpl implements AdresseDao {
    @Override
    public Adresse get(Integer id) {
        return realm.where(Adresse.class).equalTo("id_adresses",id).findFirst();
    }

    @Override
    public List<Adresse> list() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Adresse write(Adresse obj) {
        return null;
    }
}
