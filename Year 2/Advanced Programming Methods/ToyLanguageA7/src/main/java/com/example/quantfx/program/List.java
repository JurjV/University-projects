package com.example.quantfx.program;

public class List<T> implements GenericList<T>
{
    java.util.List<T> list;

    public List()
    {
        list = new java.util.ArrayList<T>();
    }

    public List(java.util.List<T> list)
    {
        this.list = list;
    }

    @Override
    public void add(T element)
    {
        list.add(element);
    }

    @Override
    public T get(int index)
    {
        return list.get(index);
    }

    @Override
    public int size()
    {
        return list.size();
    }

    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    @Override
    public List<T> deepCopy()
    {
        List<T> newList = new List<T>();
        for (T element : list)
            newList.add(element);
        return newList;
    }

    @Override
    public String toString()
    {
        String result = "";
        for (T element : list)
            result += element.toString() + "\n";
        return result;
    }

    public java.util.List<T> getContent()
    {
        return list;
    }
}
