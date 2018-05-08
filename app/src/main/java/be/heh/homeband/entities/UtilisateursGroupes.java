package be.heh.homeband.entities;

import io.realm.RealmObject;

public class UtilisateursGroupes extends RealmObject {

    private int id_utilisateurs;
    private int id_groupes;

    public UtilisateursGroupes() {
    }

    public int getId_utilisateurs() {
        return id_utilisateurs;
    }

    public void setId_utilisateurs(int id_utilisateurs) {
        this.id_utilisateurs = id_utilisateurs;
    }

    public int getId_groupes() {
        return id_groupes;
    }

    public void setId_groupes(int id_groupes) {
        this.id_groupes = id_groupes;
    }
}
