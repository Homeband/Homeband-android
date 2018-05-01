package be.heh.homeband.DaoImpl;

import io.realm.Realm;

public abstract class DaoImpl {

    protected Realm realm;

    public DaoImpl() {
         realm = Realm.getDefaultInstance();
    }
}
