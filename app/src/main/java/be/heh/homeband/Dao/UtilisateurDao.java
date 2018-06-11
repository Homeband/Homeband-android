package be.heh.homeband.Dao;

import be.heh.homeband.entities.Evenement;
import be.heh.homeband.entities.Groupe;
import be.heh.homeband.entities.Utilisateur;

public interface UtilisateurDao extends Dao<Integer,Utilisateur> {
    Utilisateur getConnectedUser();
    void disconnectUser();

    void addGroup(int id_utilisateurs, Groupe groupe);
    void deleteGroup(int id_utilisateurs, int id_groupes);
    Groupe getGroup(int id_utilisateurs, int id_groupes);

    void addEvent(int id_utilisateurs, Evenement evenement);
    void deleteEvent(int id_utilisateurs, int id_evenements);
    Evenement getEvent(int id_utilisateurs, int id_evenements);

}
