package com.example.quantfx.model.values;

import com.example.quantfx.model.types.BoolType;
import com.example.quantfx.model.types.Type;

import java.util.concurrent.CopyOnWriteArrayList;

public class BoolValue extends Value
{
    private boolean _value;

    public BoolValue(boolean value)
    {
        _value = value;
    }

    public boolean getValue()
    {
        return _value;
    }

    @Override
    public String toString()
    {
        return _value ? "True" : "False";
    }

    @Override
    public Type getType()
    {
        return new BoolType();
    }

    @Override
    public Value deepCopy()
    {
        return new BoolValue(_value);
    }

    @Override
    public boolean isEquals(Value other)
    {
        if (other instanceof BoolValue)
            return ((BoolValue) other).getValue() == _value;
        else
            return false;
    }

    @Override
    public boolean isNotEqual(Value other)
    {
        if (other instanceof BoolValue)
            return ((BoolValue) other).getValue() != _value;
        else
            return false;
    }

    @Override
    public Value and(Value other)
    {
        if (other instanceof BoolValue)
            return new BoolValue(((BoolValue) other).getValue() && _value);
        else
            return null;
    }

    @Override
    public Value or(Value other)
    {
        if (other instanceof BoolValue)
            return new BoolValue(((BoolValue) other).getValue() || _value);
        else
            return null;
    }

    @Override
    public Value not()
    {
        return new BoolValue(!_value);
    }

    @Override
    public Value relate(Value other, LogicalOperator operator)
    {
        if (!(other instanceof BoolValue))
            return null;

        boolean result = switch (operator)
        {
            case AND -> ((BoolValue) other).getValue() && _value;
            case OR -> ((BoolValue) other).getValue() || _value;
            case NOT -> !((BoolValue) other).getValue();
        };
        return new BoolValue(result);
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof BoolValue)
            return ((BoolValue) other).getValue() == _value;
        else
            return false;
    }
}
