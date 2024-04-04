package com.example.quantfx.model.values;

import com.example.quantfx.model.types.IntType;
import com.example.quantfx.model.types.Type;

public class IntValue extends Value
{
    int _value;

    public IntValue(int value)
    {
         _value = value;
    }

    public int getValue()
    {
        return _value;
    }

    @Override
    public String toString()
    {
        return "" + _value;
    }

    @Override
    public boolean compare(Value other, RelationOperator operator)
    {
        if (!(other instanceof IntValue))
            return false;

        int otherValue = ((IntValue) other).getValue();
        return switch (operator)
        {
            case EQUALS -> _value == otherValue;
            case NOT_EQUALS -> _value != otherValue;
            case LESS_THAN -> _value < otherValue;
            case LESS_OR_EQUAL -> _value <= otherValue;
            case GREATER_THAN -> _value > otherValue;
            case GREATER_OR_EQUAL -> _value >= otherValue;
            default -> false;
        };
    }

    @Override
    public Value relate(Value other, LogicalOperator operator)
    {
        if (!(other instanceof IntValue))
            return null;

        int otherValue = ((IntValue) other).getValue();
        return switch (operator)
        {
            case AND -> new IntValue(_value & otherValue);
            case OR -> new IntValue(_value | otherValue);
            case NOT -> new IntValue(_value ^ otherValue);
            default -> null;
        };
    }

    @Override
    public Value perform(Value other, ArithmeticOperator operator)
    {
        if (!(other instanceof IntValue))
            return null;

        int otherValue = ((IntValue) other).getValue();
        return switch (operator)
        {
            case ADD -> new IntValue(_value + otherValue);
            case SUBTRACT -> new IntValue(_value - otherValue);
            case MULTIPLY -> new IntValue(_value * otherValue);
            case DIVIDE -> new IntValue(_value / otherValue);
            default -> null;
        };
    }

    @Override
    public Type getType()
    {
        return new IntType();
    }

    @Override
    public Value deepCopy()
    {
        return new IntValue(_value);
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof IntValue)
            return ((IntValue) other).getValue() == _value;
        else
            return false;
    }

    @Override
    public boolean isEquals(Value other)
    {
        if (other instanceof IntValue)
            return ((IntValue) other).getValue() == _value;
        else
            return false;
    }

    @Override
    public boolean isLessThan(Value other)
    {
        if (other instanceof IntValue)
            return ((IntValue) other).getValue() < _value;
        else
            return false;
    }

    @Override
    public boolean isLessOrEqual(Value other)
    {
        if (other instanceof IntValue)
            return ((IntValue) other).getValue() <= _value;
        else
            return false;
    }

    @Override
    public boolean isGreaterThan(Value other)
    {
        if (other instanceof IntValue)
            return ((IntValue) other).getValue() > _value;
        else
            return false;
    }

    @Override
    public boolean isGreaterOrEqual(Value other)
    {
        if (other instanceof IntValue)
            return ((IntValue) other).getValue() >= _value;
        else
            return false;
    }

    @Override
    public boolean isNotEqual(Value other)
    {
        if (other instanceof IntValue)
            return ((IntValue) other).getValue() != _value;
        else
            return false;
    }

    @Override
    public Value add(Value other)
    {
        if (other instanceof IntValue)
            return new IntValue(_value + ((IntValue) other).getValue());
        else
            return null;
    }

    @Override
    public Value subtract(Value other)
    {
        if (other instanceof IntValue)
            return new IntValue(_value - ((IntValue) other).getValue());
        else
            return null;
    }

    @Override
    public Value multiply(Value other)
    {
        if (other instanceof IntValue)
            return new IntValue(_value * ((IntValue) other).getValue());
        else
            return null;
    }

    @Override
    public Value divide(Value other)
    {
        if (other instanceof IntValue)
            return new IntValue(_value / ((IntValue) other).getValue());
        else
            return null;
    }
}
