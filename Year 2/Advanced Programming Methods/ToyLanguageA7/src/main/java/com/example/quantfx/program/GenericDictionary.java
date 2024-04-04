package com.example.quantfx.program;

import com.example.quantfx.exceptions.CollectionException;
import com.example.quantfx.model.types.Type;

public interface GenericDictionary<Key, Value>
{
    void add(Key key, Value value) throws CollectionException;
    void remove(Key key) throws CollectionException;
    Value get(Key key);
    void update(Key key, Value value);
    String toString();
    boolean containsKey(Key key);
    GenericDictionary<Key, Value> deepCopy();

    java.util.Map<Key, Value> getContent();
}
