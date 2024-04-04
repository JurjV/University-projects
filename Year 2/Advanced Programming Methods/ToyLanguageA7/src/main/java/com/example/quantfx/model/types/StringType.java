package com.example.quantfx.model.types;

import com.example.quantfx.model.values.StringValue;
import com.example.quantfx.model.values.Value;

public class StringType implements Type
{
    @Override
    public boolean equals(Object other)
    {
        return other instanceof StringType;
    }

    @Override
    public Value defaultValue()
    {
        return new StringValue("");
    }

    @Override
    public String toString()
    {
        return "String";
    }
}
