package be.heh.homeband.entities;
import java.util.Date;

/**
 * @author Nicolas
 * @version 1.0
 * @created 26-janv.-2018 14:38:24
 */
public class Membre {

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

}