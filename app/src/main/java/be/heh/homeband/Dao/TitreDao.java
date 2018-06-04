package be.heh.homeband.Dao;

import be.heh.homeband.entities.Titre;

public interface TitreDao extends Dao<Integer,Titre> {
    void deleteByGroup (int id_groupes);
}
