package com.example.quantfx.model.types;

import com.example.quantfx.model.values.IntValue;
import com.example.quantfx.model.values.Value;

public class IntType implements Type
{
    @Override
    public boolean equals(Object other)
    {
        return other instanceof IntType;
    }

    @Override
    public Value defaultValue()
    {
        return new IntValue(0);
    }

    @Override
    public String toString()
    {
        return "Int";
    }
}
