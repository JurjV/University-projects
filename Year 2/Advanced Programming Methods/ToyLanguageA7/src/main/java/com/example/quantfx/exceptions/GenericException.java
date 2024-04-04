package com.example.quantfx.exceptions;

public class GenericException extends Exception
{
    public GenericException(Object o)
    {
        System.out.println(o.toString());
    }
}
