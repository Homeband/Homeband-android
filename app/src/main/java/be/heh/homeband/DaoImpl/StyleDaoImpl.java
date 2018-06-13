package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.StyleDao;
import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Style;

public class StyleDaoImpl extends DaoImpl implements StyleDao {
    @Override
    public Style get(Integer id) {
        Style result = realm.where(Style.class)
                .equalTo("id_styles",id)
                .findFirst();
        if (result != null){
            return realm.copyFromRealm(result);
        }

        return null;
    }

    @Override
    public List<Style> list() {

        return realm.copyFromRealm(realm.where(Style.class).findAll()) ;

    }

    @Override
    public void delete(Integer id) {
        realm.beginTransaction();
        Style result = realm.where(Style.class)
                .equalTo("id_styles",id)
                .findFirst();
        if (result != null){
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    @Override
    public Style write(Style obj) {
        return null;
    }
}
