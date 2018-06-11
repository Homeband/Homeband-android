package be.heh.homeband.Dao;

import java.util.List;

public interface Dao<K,E> {

    E get(K id);

     List<E> list();

     void delete(K id);

     E write(E obj);
}
