package be.heh.homeband.Dao;

import java.util.List;

import be.heh.homeband.entities.Membre;

public interface MembreDao extends Dao<Integer,Membre> {
    List<Membre> getByGroupe (int id_groupe);
    void deleteByGroup (int id_groupes);
}
