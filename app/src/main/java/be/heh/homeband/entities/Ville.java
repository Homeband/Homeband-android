package be.heh.homeband.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Ville extends RealmObject {

	@PrimaryKey
	private int id_villes;
	private String code_postal;
	private String nom;
	private double lat;
	private double lon;
	private boolean est_actif;

	public int getId_villes() {
		return id_villes;
	}

	public void setId_villes(int id_villes) {
		this.id_villes = id_villes;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public boolean isEst_actif() {
		return est_actif;
	}

	public void setEst_actif(boolean est_actif) {
		this.est_actif = est_actif;
	}


	public Ville(){

	}

}