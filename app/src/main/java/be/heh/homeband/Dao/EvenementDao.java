package be.heh.homeband.Dao;

import java.util.List;

import be.heh.homeband.entities.Evenement;

public interface EvenementDao extends Dao<Integer,Evenement> {
    List<Evenement> listByGroup (int id_groupes);
    List<Evenement> listByUser(int id_utilisateurs);
    boolean isUsed(int id_evenements);
}
