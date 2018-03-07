package be.heh.homeband.app;

import be.heh.homeband.entities.Utilisateur;
import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by christopher on 07/03/2018.
 */

public abstract class HomebandTools {

    public static Utilisateur getConnectedUser() {
        Realm realm = Realm.getDefaultInstance();
        // Build the query looking at all users:
        RealmQuery<Utilisateur> query = realm.where(Utilisateur.class);

        // Add query conditions:
        query.equalTo("est_connecte", true);

        // Execute the query:
        Utilisateur user = query.findFirst();
        return user;
    }

    public static void disconnectUser() {
        final Utilisateur user = getConnectedUser();
        if (user != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    user.setEst_connecte(false);
                    realm.copyToRealmOrUpdate(user);
                }
            });
        }
    }
}
