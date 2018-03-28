package be.heh.homeband.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Nicolas
 * @version 1.0
 * @created 26-janv.-2018 14:38:24
 */
public class Style extends RealmObject {

    @PrimaryKey
	private int id_styles;
	private String nom;
	private boolean est_actif;

	public Style(){

	}

	@Override
	public String toString() {
		return this.nom;
	}

	public int getId_styles() {
		return id_styles;
	}

	public void setId_styles(int id_styles) {
		this.id_styles = id_styles;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isEst_actif() {
		return est_actif;
	}

	public void setEst_actif(boolean est_actif) {
		this.est_actif = est_actif;
	}
}