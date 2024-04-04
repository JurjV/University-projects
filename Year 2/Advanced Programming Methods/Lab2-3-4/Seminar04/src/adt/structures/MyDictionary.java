package adt.structures;

import controller.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private Map<K, V> map;

    public MyDictionary() {
        map = new HashMap<K, V>();
    }

    @Override
    public void put(K k, V v) {
        map.put(k, v);
    }

    @Override
    public boolean isDefined(K k) {
        return map.get(k) != null;
    }

    @Override
    public V lookUp(K k) {
        return map.get(k);
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public String toString() {
        return "MyDictionary{" +
                "map=" + map +
                '}';
    }

    @Override
    public Map<K, V> getContent() {
        synchronized (this) {
            return this.map;
        }
    }

    public Map<K, V> getMap() {
        return map;
    }

    public void setMap(Map<K, V> map) {
        this.map = map;
    }

    public void remove(K k) throws MyException {
        if (!isDefined(k))
            throw new MyException(k + " is not defined.");
        this.map.remove(k);
    }
}
