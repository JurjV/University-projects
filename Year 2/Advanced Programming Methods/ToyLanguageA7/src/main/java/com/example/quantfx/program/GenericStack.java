package com.example.quantfx.program;

import com.example.quantfx.exceptions.CollectionException;

public interface GenericStack<T>
{
    void push(T element);
    T pop() throws CollectionException;
    boolean isEmpty();

    GenericStack<T> deepCopy();
}
