package com.example.quantfx.model.values;

import com.example.quantfx.model.types.StringType;
import com.example.quantfx.model.types.Type;

import java.nio.channels.Pipe;

public class StringValue extends Value
{
    String value;

    public StringValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public Type getType()
    {
        return new StringType();
    }

    @Override
    public String toString()
    {
        return value;
    }

    @Override
    public Value deepCopy()
    {
        return new StringValue(value);
    }

    @Override
    public int hashCode()
    {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof StringValue)
            return ((StringValue) other).getValue().equals(value);
        else
            return false;
    }
}
