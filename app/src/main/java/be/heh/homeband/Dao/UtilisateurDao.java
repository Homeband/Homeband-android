package be.heh.homeband.Dao;

import be.heh.homeband.entities.Utilisateur;

public interface UtilisateurDao extends Dao<Integer,Utilisateur> {
    Utilisateur getConnectedUser();
    void disconnectUser();
}
