package be.heh.homeband.app;

import android.util.Log;

import java.util.Date;

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
        if (oldVersion == 3) {
            schema.create("Adresse")
                    .addField("id_adresses", int.class, FieldAttribute.PRIMARY_KEY)
                    .addField("rue", String.class)
                    .addField("numero", int.class)
                    .addField("boite", String.class)
                    .addField("lat", double.class)
                    .addField("lon", double.class)
                    .addField("id_villes", int.class)
                    .addField("est_actif",boolean.class);

            schema.create("Album")
                    .addField("id_albums", int.class, FieldAttribute.PRIMARY_KEY)
                    .addField("titre", String.class)
                    .addField("image", String.class)
                    .addField("date_sortie", Date.class)
                    .addField("id_groupes", int.class)
                    .addField("est_actif",boolean.class);

            schema.create("Evenement")
                    .addField("id_evenements", int.class, FieldAttribute.PRIMARY_KEY)
                    .addField("nom", String.class)
                    .addField("description", String.class)
                    .addField("date_heure", Date.class)
                    .addField("prix", double.class)
                    .addField("id_groupes", int.class)
                    .addField("id_adresses", int.class)
                    .addField("est_actif",boolean.class);

            schema.create("Membre")
                    .addField("id_membres", int.class, FieldAttribute.PRIMARY_KEY)
                    .addField("nom", String.class)
                    .addField("prenom", String.class)
                    .addField("est_date", boolean.class)
                    .addField("date_debut", Date.class)
                    .addField("date_fin", Date.class)
                    .addField("est_actif",boolean.class)
                    .addField("id_groupes", int.class);

            schema.create("Titre")
                    .addField("id_titres", int.class, FieldAttribute.PRIMARY_KEY)
                    .addField("titre", String.class)
                    .addField("date_sortie", Date.class)
                    .addField("est_actif", boolean.class)
                    .addField("id_groupes", int.class)
                    .addField("id_albums", int.class);

            oldVersion++;
        }
        if (oldVersion == 4) {
            schema.create("UtilisateursGroupes")
                    .addField("id_utilisateurs", int.class)
                    .addField("id_groupes", int.class);

            oldVersion++;
        } if (oldVersion == 5) {
            schema.create("Groupe")
                    .addField("id_groupes", int.class)
                    .addField("nom", String.class)
                    .addField("login", String.class)
                    .addField("mot_de_passe", String.class)
                    .addField("email", String.class)
                    .addField("biographie", String.class)
                    .addField("contacts", String.class)
                    .addField("lien_itunes", String.class)
                    .addField("lien_youtube", String.class)
                    .addField("lien_spotify", String.class)
                    .addField("lien_souncloud", String.class)
                    .addField("lien_bandcamp", String.class)
                    .addField("lien_twitter", String.class)
                    .addField("lien_instagram", String.class)
                    .addField("lien_facebook", String.class)
                    .addField("api_ck", String.class)
                    .addField("est_actif", boolean.class)
                    .addField("id_styles", int.class)
                    .addField("id_villes", int.class);

            oldVersion++;
        }
    }
}
