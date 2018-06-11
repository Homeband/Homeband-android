package be.heh.homeband.entities;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;


public class Evenement extends RealmObject implements Serializable {

	@PrimaryKey
	private int id_evenements;
	private String nom;
	private String description;
	private Date date_heure;
	private double prix ;
	private boolean est_actif;
	private int id_groupes;
	private int id_adresses;



	private String illustration;

	@LinkingObjects("events")
	private final RealmResults<Utilisateur> users = null;

	@Ignore
	private int id_villes;

	public Evenement() {
	}

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

	public Date getDate_heure() {
		return date_heure;
	}

	public void setDate_heure(Date date_heure) {
		this.date_heure = date_heure;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getId_adresses() {
		return id_adresses;
	}

	public void setId_adresses(int id_adresses) {
		this.id_adresses = id_adresses;
	}

	public int getId_villes() {
		return id_villes;
	}

	public void setId_villes(int id_villes) {
		this.id_villes = id_villes;
	}

	public RealmResults<Utilisateur> getUsers() {
		return users;
	}

	public String getIllustration() {
		return illustration;
	}

	public void setIllustration(String illustration) {
		this.illustration = illustration;
	}

}