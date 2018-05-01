package be.heh.homeband.Dao;

import be.heh.homeband.entities.Version;

public interface VersionDao<K,E> extends Dao<K,E> {

    public Version getByNomTable(String nomtable);
}
