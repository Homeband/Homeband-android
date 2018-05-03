package be.heh.homeband.entities;
import java.util.Date;


public class Newsletter {

	private int id_news;
	private String sujet;
	private String contenu;
	private Date date_envoi;
	private boolean est_envoyee;
	private boolean est_groupe;
	private boolean est_utilisateur;
	private boolean est_actif;
	private int id_administrateurs;

	public Newsletter(){

	}

}