package be.heh.homeband.Dao;

import be.heh.homeband.entities.Version;

public interface VersionDao<K,E> extends Dao<K,E> {

    Version getByNomTable(String nomtable);
}
