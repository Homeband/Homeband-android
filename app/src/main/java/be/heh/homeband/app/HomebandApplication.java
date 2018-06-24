package be.heh.homeband.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created on 21/03/2018.
 */

public class HomebandApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("MyRealm.realm")
                .schemaVersion(10)
                .migration(new HomebandRealmMigration())
                .build();
        Realm.setDefaultConfiguration(config);


    }
}
