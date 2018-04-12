package be.heh.homeband.entities;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @author Nicolas
 * @version 1.0
 * @created 26-janv.-2018 14:38:24
 */
public class Evenement extends RealmObject implements Serializable {

	@PrimaryKey
	private int id_evenements;
	private String nom;
	private String description;
	private boolean est_actif;
	private int id_groupes;

	public Evenement(int id_groupes, String nom){
		this.id_evenements = id_evenements;
		this.nom = nom;
	}

	public int getId_evenements() {
		return id_evenements;
	}

	public void setId_evenements(int id_evenements) {
		this.id_evenements = id_evenements;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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