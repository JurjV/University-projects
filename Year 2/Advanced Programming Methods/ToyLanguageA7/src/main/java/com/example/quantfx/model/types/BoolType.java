package com.example.quantfx.model.types;

import com.example.quantfx.model.values.BoolValue;
import com.example.quantfx.model.values.Value;

public class BoolType implements Type
{
    @Override
    public boolean equals(Object other)
    {
        return other instanceof BoolType;
    }

    @Override
    public Value defaultValue()
    {
        return new BoolValue(false);
    }

    @Override
    public String toString()
    {
        return "Bool";
    }
}
