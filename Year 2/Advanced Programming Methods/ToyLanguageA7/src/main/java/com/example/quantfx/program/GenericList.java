package com.example.quantfx.program;

public interface GenericList<T>
{
    void add(T element);
    T get(int index);
    int size();
    boolean isEmpty();
    GenericList<T> deepCopy();
}
