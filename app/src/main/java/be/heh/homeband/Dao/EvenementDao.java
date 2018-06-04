package be.heh.homeband.Dao;

import java.util.List;

import be.heh.homeband.entities.Evenement;

public interface EvenementDao extends Dao<Integer,Evenement> {
    List<Evenement> listByGroup (int id_groupes);
    void deleteByGroup (int id_groupes);
}
