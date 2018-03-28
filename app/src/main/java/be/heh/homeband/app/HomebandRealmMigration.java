package be.heh.homeband.app;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class HomebandRealmMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if (oldVersion == 2) {
            schema.create("Style")
                    .addField("id_styles", int.class, FieldAttribute.PRIMARY_KEY)
                    .addField("nom", String.class)
                    .addField("est_actif",boolean.class);
            oldVersion++;
        }
    }
}
