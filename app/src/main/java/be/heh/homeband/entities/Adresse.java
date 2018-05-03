package be.heh.homeband.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Adresse extends RealmObject {

	@PrimaryKey
	private int id_adresses;
	private String rue;
	private int numero;
	private String boite;
	private double lat;
	private double lon;
	private boolean est_actif;
	private int id_villes;

	public Adresse(){

	}

	public int getId_adresses() {
		return id_adresses;
	}

	public void setId_adresses(int id_adresses) {
		this.id_adresses = id_adresses;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getBoite() {
		return boite;
	}

	public void setBoite(String boite) {
		this.boite = boite;
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

	public int getId_villes() {
		return id_villes;
	}

	public void setId_villes(int id_villes) {
		this.id_villes = id_villes;
	}
}