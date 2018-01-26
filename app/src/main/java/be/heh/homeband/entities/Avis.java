package be.heh.homeband.entities;
import java.util.Date;

/**
 * @author Nicolas
 * @version 1.0
 * @created 26-janv.-2018 14:38:24
 */
public class Avis {

	private int id_avis;
	private String commentaire;
	private boolean est_verifie;
	private boolean est_accepte;
	private Date date_ajout;
	private Date date_validation;
	private int est_actif;
	private int id_groupes;
	private int id_utilisateurs;

	public Avis(){

	}

}