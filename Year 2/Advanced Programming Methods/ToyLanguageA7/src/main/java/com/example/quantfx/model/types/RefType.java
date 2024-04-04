package com.example.quantfx.model.types;
import com.example.quantfx.model.values.RefValue;
import com.example.quantfx.model.values.Value;

public class RefType implements Type
{
    Type inner;

    public RefType(Type inner)
    {
        this.inner = inner;
    }

    public RefType()
    {
        this.inner = null;
    }

    public Type getInner()
    {
        return inner;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof RefType)
            return this.inner.equals(((RefType) other).getInner());
        else
            return false;
    }

    @Override
    public Value defaultValue()
    {
        return new RefValue(0, inner);
    }

    @Override
    public String toString() { return "Ref(" +inner.toString()+")";}
}
