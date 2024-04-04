package com.example.quantfx.program;

import com.example.quantfx.model.values.StringValue;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.HashMap;

public interface GenericHeap<Key, Value>
{
    public Collection<Value> values();

    // Checks
    public boolean containsKey(Key key);
    public boolean containsValue(Value value);
    public boolean isEmpty();

    // Setters
    public void clear();
    public void update(Key key, Value newValue);
    public void insert(Key key, Value newValue);
    public void setContent(java.util.HashMap<Key, Value> newHeap);
    public void setFirstAvailablePosition();

    // Getters
    public int size();
    public int getFirstAvailablePosition();
    public Value get(Key key);
    public Value remove(Key key);

    public Heap<Key, Value> deepCopy();

    HashMap<Key,Value> getContent();
}
