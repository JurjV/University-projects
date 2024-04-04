package adt.structures;

import controller.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K, V> {
    void put(K k, V v);

    boolean isDefined(K k);

    V lookUp(K k);

    Collection<V> values();

    Set<K> keySet();

    void remove(K k) throws MyException;

    Map<K, V> getContent();

}
