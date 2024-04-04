package com.example.quantfx.program;

import com.example.quantfx.exceptions.CollectionException;

public class Stack<T> implements GenericStack<T>
{
    java.util.Stack<T> stack;

    public Stack()
    {
        stack = new java.util.Stack<T>();
    }

    @Override
    public void push(T element)
    {
        stack.push(element);
    }

    @Override
    public T pop() throws CollectionException
    {
        if(isEmpty())
            throw new CollectionException("Stack is empty!");
        return stack.pop();
    }

    @Override
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    public java.util.List<T> getContents()
    {
        return stack;
    }

    @Override
    public Stack<T> deepCopy()
    {
        Stack<T> newStack = new Stack<T>();
        for (T element : stack)
            newStack.push(element);
        return newStack;
    }

    @Override
    public String toString()
    {
        String result = "";
        for (T element : stack)
            result += element.toString() + "\n";
        return result;
    }
}
