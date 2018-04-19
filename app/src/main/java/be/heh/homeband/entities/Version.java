package be.heh.homeband.entities;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by christopher on 21/03/2018.
 */

public class Version extends RealmObject {

    @PrimaryKey
    private int id_versions;
    private int num_table;
    private String nom_table;
    private Date date_maj;

    public Version() {
    }

    public int getId_versions() {
        return id_versions;
    }

    public void setId_versions(int id_versions) {
        this.id_versions = id_versions;
    }

    public int getNum_table() {
        return num_table;
    }

    public void setNum_table(int num_table) {
        this.num_table = num_table;
    }

    public String getNom_table() {
        return nom_table;
    }

    public void setNom_table(String nom_table) {
        this.nom_table = nom_table;
    }

    public Date getDate_maj() {
        return date_maj;
    }

    public void setDate_maj(Date date_maj) {
        this.date_maj = date_maj;
    }


}
