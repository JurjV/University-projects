package com.example.quantfx.model.expressions;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.types.RefType;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.RefValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.Heap;

public class ReadHeapExpression implements Expression
{
    private Expression expression;

    public ReadHeapExpression(Expression expression){
        this.expression = expression;
    }

    @Override
    public Value evaluate(Dictionary<String, Value> table, Heap<Integer, Value> heap) throws GenericException
    {
        Value val = expression.evaluate(table, heap);
        if (!(val.getType() instanceof RefType))
            throw new GenericException("The expression is not a RefValue!\n");

        RefValue refVal = (RefValue)val;
        int address = refVal.address;
        if (!heap.containsKey(address))
            throw new GenericException("The address is not in the heap!\n");

        return heap.get(address);
    }

    @Override
    public Type typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        Type type = expression.typeCheck(typeEnv);
        if(!(type instanceof RefType))
            throw new GenericException("The readHeap argument is not a RefType!\n");

        return ((RefType) type).getInner();
    }

    @Override
    public Expression deepCopy()
    {
        return new ReadHeapExpression(this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "readHeap("+expression.toString()+")";
    }
}
