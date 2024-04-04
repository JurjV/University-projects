package com.example.quantfx.program;

import com.example.quantfx.exceptions.CollectionException;
import com.example.quantfx.model.types.Type;

import java.util.List;
import java.util.Set;

public class Dictionary<Key, Value> implements GenericDictionary<Key, Value>
{
    java.util.Map<Key, Value> dictionary;

    public Dictionary()
    {
        dictionary = new java.util.HashMap<Key, Value>();
    }

    @Override
    public void add(Key key, Value value) throws CollectionException
    {
        if(dictionary.containsKey(key))
            throw new CollectionException("Key: " + key.toString() + " already present in dictionary.");

        dictionary.put(key, value);
    }

    public String toKeyString()
    {
        String result = "";
        for (Key key : dictionary.keySet())
        {
            result += key.toString() + "\n";
        }

        return result;
    }

    public List<Key> getKeys()
    {
        Set<Key> keys = dictionary.keySet();
        List<Key> keyList = new java.util.ArrayList<Key>();

        for (Key key : keys)
        {
            keyList.add(key);
        }

        return keyList;
    }

    public List<Value> getValues()
    {
        return (List<Value>) dictionary.values();
    }

    @Override
    public void remove(Key key) throws CollectionException
    {
        if(!dictionary.containsKey(key))
            throw new CollectionException("Key: " + key.toString() + " not present in dictionary.");

        dictionary.remove(key);
    }

    @Override
    public Value get(Key key)
    {
        return dictionary.get(key);
    }

    @Override
    public void update(Key key, Value value)
    {
        dictionary.put(key, value);
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        for (Key key : dictionary.keySet())
        {
            try
            {
                result.append(key.toString()).append(" -> ").append(dictionary.get(key).toString()).append("\n");
            }
            catch (Exception e)
            {
                continue;
            }
        }

        return result.toString();
    }

    @Override
    public boolean containsKey(Key key)
    {
        return dictionary.containsKey(key);
    }

    @Override
    public Dictionary<Key, Value> deepCopy()
    {
        Dictionary<Key, Value> newDictionary = new Dictionary<Key, Value>();
        for (Key key : dictionary.keySet())
        {
            try
            {
                newDictionary.add(key, dictionary.get(key));
            }
            catch (Exception e)
            {
                continue;
            }
        }

        return newDictionary;
    }

    @Override
    public java.util.Map<Key, Value> getContent()
    {
        return dictionary;
    }
}
