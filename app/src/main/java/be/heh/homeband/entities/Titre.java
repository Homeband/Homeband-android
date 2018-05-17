package be.heh.homeband.entities;
import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Titre extends RealmObject implements Serializable {

	@PrimaryKey
	private int id_titres;
	private String titre;
	private Date date_sortie;
	private boolean est_actif;
	private int id_groupes;
	private int id_albums;

	public Titre(){

	}

	public int getId_titres() {
		return id_titres;
	}

	public void setId_titres(int id_titres) {
		this.id_titres = id_titres;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Date getDate_sortie() {
		return date_sortie;
	}

	public void setDate_sortie(Date date_sortie) {
		this.date_sortie = date_sortie;
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

	public int getId_albums() {
		return id_albums;
	}

	public void setId_albums(int id_albums) {
		this.id_albums = id_albums;
	}
}