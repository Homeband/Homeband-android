package be.heh.homeband.entities;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import okhttp3.internal.Util;


public class Groupe extends RealmObject implements Serializable {

	@PrimaryKey
	private int id_groupes;
	private String nom;
	private String login;
	private String mot_de_passe;
	private String email;
	private String biographie;
	private String contacts;
	private String lien_itunes;
	private String lien_youtube;
	private String lien_spotify;
	private String lien_soundcloud;
	private String lien_bandcamp;
	private String lien_twitter;
	private String lien_instagram;
	private String lien_facebook;
	private String api_ck;
	private boolean est_actif;
	private int id_styles;
	private int id_villes;

	@LinkingObjects("groups")
	private final  RealmResults<Utilisateur> users = null;
	private RealmList<Evenement> events;

	public Groupe(){

	}

	public Groupe(int id_groupes, String nom){
		this.id_groupes = id_groupes;
		this.nom = nom;
	}

	public int getId_groupes() {
		return id_groupes;
	}

	public void setId_groupes(int id_groupes) {
		this.id_groupes = id_groupes;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public String getBiographie() {
		return biographie;
	}

	public void setBiographie(String biographie) {
		this.biographie = biographie;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getLien_itunes() {
		return lien_itunes;
	}

	public void setLien_itunes(String lien_itunes) {
		this.lien_itunes = lien_itunes;
	}

	public String getLien_youtube() {
		return lien_youtube;
	}

	public void setLien_youtube(String lien_youtube) {
		this.lien_youtube = lien_youtube;
	}

	public String getLien_spotify() {
		return lien_spotify;
	}

	public void setLien_spotify(String lien_spotify) {
		this.lien_spotify = lien_spotify;
	}

	public String getLien_soundcloud() {
		return lien_soundcloud;
	}

	public void setLien_soundcloud(String lien_soundcloud) {
		this.lien_soundcloud = lien_soundcloud;
	}

	public String getLien_bandcamp() {
		return lien_bandcamp;
	}

	public void setLien_bandcamp(String lien_bandcamp) {
		this.lien_bandcamp = lien_bandcamp;
	}

	public String getLien_twitter() {
		return lien_twitter;
	}

	public void setLien_twitter(String lien_twitter) {
		this.lien_twitter = lien_twitter;
	}

	public String getLien_instagram() {
		return lien_instagram;
	}

	public void setLien_instagram(String lien_instagram) {
		this.lien_instagram = lien_instagram;
	}

	public String getLien_facebook() {
		return lien_facebook;
	}

	public void setLien_facebook(String lien_facebook) {
		this.lien_facebook = lien_facebook;
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

	public int getId_styles() {
		return id_styles;
	}

	public void setId_styles(int id_styles) {
		this.id_styles = id_styles;
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

	public RealmList<Evenement> getEvents() {
		return events;
	}

	public void setEvents(RealmList<Evenement> events) {
		this.events = events;
	}
}