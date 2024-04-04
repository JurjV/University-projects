package com.example.quantfx.model.expressions;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.Heap;

public interface Expression
{
    Value evaluate(Dictionary<String, Value> table, Heap<Integer, Value> heap) throws GenericException;

    Type typeCheck(Dictionary<String, Type> typeEnv) throws GenericException;

    Expression deepCopy();
}
