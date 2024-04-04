package com.example.quantfx.model.expressions;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.GenericDictionary;
import com.example.quantfx.program.Heap;

public class ValueExpression implements Expression
{
    private Value value;

    public ValueExpression(Value value)
    {
        this.value = value;
    }

    @Override
    public Value evaluate(Dictionary<String, Value> table, Heap<Integer, Value> heap) throws GenericException
    {
        return value;
    }

    @Override
    public Type typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        return value.getType();
    }

    @Override
    public Expression deepCopy()
    {
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public String toString()
    {
        return value.toString();
    }
}