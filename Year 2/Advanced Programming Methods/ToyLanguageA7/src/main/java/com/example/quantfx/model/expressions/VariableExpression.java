package com.example.quantfx.model.expressions;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.GenericDictionary;
import com.example.quantfx.program.Heap;

public class VariableExpression implements Expression
{
    private String name;

    public VariableExpression(String name)
    {
        this.name = name;
    }

    @Override
    public Value evaluate(Dictionary<String, Value> table, Heap<Integer, Value> heap) throws GenericException
    {
        return table.get(name);
    }

    @Override
    public Type typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        return typeEnv.get(name);
    }

    @Override
    public Expression deepCopy()
    {
        return new VariableExpression(name);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
