package be.heh.homeband.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Nicolas
 * @version 1.0
 * @created 26-janv.-2018 14:38:24
 */
public class Utilisateur extends RealmObject {

    @PrimaryKey
	private int id_utilisateurs;
	private String login;
	private String mot_de_passe;
	private String email;
	private String nom;
	private String prenom;
	private String api_ck;
	private boolean est_actif;
	private boolean est_connecte;

	public Utilisateur(){

	}

    public int getId_utilisateurs() {
        return id_utilisateurs;
    }

    public void setId_utilisateurs(int id_utilisateurs) {
        this.id_utilisateurs = id_utilisateurs;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getApi_ck() {
        return api_ck;
    }

    public void setApi_ck(String api_ck) {
        this.api_ck = api_ck;
    }

    public boolean isEst_actif() {
        return est_actif;
    }

    public void setEst_actif(boolean est_actif) {
        this.est_actif = est_actif;
    }

    public boolean isEst_connecte() {
        return est_connecte;
    }

    public void setEst_connecte(boolean est_connecte) {
        this.est_connecte = est_connecte;
    }
}