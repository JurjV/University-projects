package com.example.quantfx.model.values;

import com.example.quantfx.model.types.Type;

public class Value
{
    public Type getType()
    {
        return null;
    }

    public Value deepCopy()
    {
        return null;
    }

    public boolean equals(Object other)
    {
        return false;
    }

    public Value or(Value other)
    {
        return null;
    }

    public Value and(Value other)
    {
        return null;
    }

    public Value not()
    {
        return null;
    }

    public String toString()
    {
        return null;
    }

    public boolean compare(Value other, RelationOperator operator)
    {
        return false;
    }

    public Value relate(Value other, LogicalOperator operator)
    {
        return null;
    }

    public Value perform(Value other, ArithmeticOperator operator)
    {
        return null;
    }

    public boolean isEquals(Value other)
    {
        return false;
    }
    public boolean isLessThan(Value other)
    {
        return false;
    }
    public boolean isLessOrEqual(Value other)
    {
        return false;
    }
    public boolean isGreaterThan(Value other)
    {
        return false;
    }
    public boolean isGreaterOrEqual(Value other)
    {
        return false;
    }
    public boolean isNotEqual(Value other)
    {
        return false;
    }

    public Value add(Value other)
    {
        return null;
    }

    public Value subtract(Value other)
    {
        return null;
    }

    public Value multiply(Value other)
    {
        return null;
    }
    public Value divide(Value other)
    {
        return null;
    }
}
