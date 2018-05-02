package be.heh.homeband.DaoImpl;

import java.util.List;

import be.heh.homeband.Dao.StyleDao;
import be.heh.homeband.entities.Style;
import be.heh.homeband.entities.Ville;

public class StyleDaoImpl extends DaoImpl implements StyleDao {
    @Override
    public Style get(Integer id) {
        return realm.where(Style.class).equalTo("id_styles",id).findFirst();
    }

    @Override
    public List<Style> list() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Style write(Style obj) {
        return null;
    }
}
