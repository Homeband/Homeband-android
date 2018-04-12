package be.heh.homeband.entities;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Nicolas
 * @version 1.0
 * @created 26-janv.-2018 14:38:24
 */
public class Membre extends RealmObject {

	@PrimaryKey
	private int id_membres;
	private String nom;
	private String prenom;
	private boolean est_date;
	private Date date_debut;
	private Date date_fin;
	private boolean est_actif;
	private int id_groupes;

	public Membre(){

	}

	public int getId_membres() {
		return id_membres;
	}

	public void setId_membres(int id_membres) {
		this.id_membres = id_membres;
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

	public boolean isEst_date() {
		return est_date;
	}

	public void setEst_date(boolean est_date) {
		this.est_date = est_date;
	}

	public Date getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}

	public boolean isEst_actif() {
		return est_actif;
	}

	public void setEst_actif(boolean est_actif) {
		this.est_actif = est_actif;
	}

	public int getId_groupes() {
		return id_groupes;
	}

	public void setId_groupes(int id_groupes) {
		this.id_groupes = id_groupes;
	}
}