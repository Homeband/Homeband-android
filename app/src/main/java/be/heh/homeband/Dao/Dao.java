package be.heh.homeband.Dao;

import java.util.ArrayList;
import java.util.List;

public interface Dao<K,E> {

    public E get(K id);

    public List<E> list();

    public void delete(K id);

    public E write(E obj);
}
