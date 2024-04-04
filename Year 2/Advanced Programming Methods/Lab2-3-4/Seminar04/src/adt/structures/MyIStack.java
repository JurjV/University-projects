package adt.structures;

import java.util.List;

public interface MyIStack<T> {
    T pop();
    void push(T e);

    boolean isEmpty();

    List<T> getReversed();
}
