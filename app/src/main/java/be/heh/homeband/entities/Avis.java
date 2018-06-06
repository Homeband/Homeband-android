package be.heh.homeband.entities;
import java.io.Serializable;
import java.util.Date;


public class Avis implements Serializable {

	private int id_avis;
	private String commentaire;
	private boolean est_verifie;
	private boolean est_accepte;
	private Date date_ajout;
	private Date date_validation;
	private int est_actif;
	private int id_groupes;
	private int id_utilisateurs;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



	public Avis(){

	}

	public int getId_avis() {
		return id_avis;
	}

	public void setId_avis(int id_avis) {
		this.id_avis = id_avis;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public boolean isEst_verifie() {
		return est_verifie;
	}

	public void setEst_verifie(boolean est_verifie) {
		this.est_verifie = est_verifie;
	}

	public boolean isEst_accepte() {
		return est_accepte;
	}

	public void setEst_accepte(boolean est_accepte) {
		this.est_accepte = est_accepte;
	}

	public Date getDate_ajout() {
		return date_ajout;
	}

	public void setDate_ajout(Date date_ajout) {
		this.date_ajout = date_ajout;
	}

	public Date getDate_validation() {
		return date_validation;
	}

	public void setDate_validation(Date date_validation) {
		this.date_validation = date_validation;
	}

	public int getEst_actif() {
		return est_actif;
	}

	public void setEst_actif(int est_actif) {
		this.est_actif = est_actif;
	}

	public int getId_groupes() {
		return id_groupes;
	}

	public void setId_groupes(int id_groupes) {
		this.id_groupes = id_groupes;
	}

	public int getId_utilisateurs() {
		return id_utilisateurs;
	}

	public void setId_utilisateurs(int id_utilisateurs) {
		this.id_utilisateurs = id_utilisateurs;
	}
}