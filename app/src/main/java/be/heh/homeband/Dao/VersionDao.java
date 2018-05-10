package be.heh.homeband.Dao;

import be.heh.homeband.entities.Style;
import be.heh.homeband.entities.Version;

public interface VersionDao extends Dao<Integer,Version> {

    Version getByNomTable(String nomtable);
}
