package com.example.quantfx.program;
import java.util.*;
import java.util.List;


public class Heap<Key, Value> implements GenericHeap<Key, Value>
{
    private HashMap<Key, Value> heap;
    int firstAvailablePosition = 1;

    public Heap()
    {
        heap = new HashMap<Key, Value>();
    }

    @Override
    public void setContent(HashMap<Key, Value> newHeap)
    {
        heap.clear();
        heap.putAll(newHeap);
    }

    public void setContent(Map<Key, Value> newHeap)
    {
        heap.clear();
        heap.putAll(newHeap);
    }

    @Override
    public void setFirstAvailablePosition()
    {
        // Find the first available position
        firstAvailablePosition = size() + 1;
    }

    @Override
    public int getFirstAvailablePosition()
    {
        int positionCopy = firstAvailablePosition;
        setFirstAvailablePosition();
        return positionCopy;
    }

    public List<Key> getKeys()
    {
        Set<Key> keys = heap.keySet();
        List<Key> keyList = new java.util.ArrayList<Key>();

        for (Key key : keys)
        {
            keyList.add(key);
        }

        return keyList;
    }

    @Override
    public int size()
    {
        return heap.size();
    }

    @Override
    public Collection<Value> values()
    {
        return heap.values();
    }

    @Override
    public boolean containsKey(Key key)
    {
        return heap.containsKey(key);
    }

    @Override
    public boolean containsValue(Value value)
    {
        return heap.containsValue(value);
    }

    @Override
    public boolean isEmpty()
    {
        return heap.isEmpty();
    }

    @Override
    public void update(Key key, Value newValue)
    {
        heap.replace(key, newValue);
    }

    @Override
    public void insert(Key key, Value newValue)
    {
        heap.put(key, newValue);
    }

    @Override
    public void clear()
    {
        heap.clear();
    }

    @Override
    public Value get(Key key)
    {
        return heap.get(key);
    }

    @Override
    public Value remove(Key key)
    {
        return heap.remove(key);
    }

    @Override
    public Heap<Key, Value> deepCopy()
    {
        Heap<Key, Value> newHeap = new Heap<Key, Value>();
        newHeap.setContent(this.heap);
        return newHeap;
    }

    @Override
    public HashMap<Key, Value> getContent()
    {
        return heap;
    }
}