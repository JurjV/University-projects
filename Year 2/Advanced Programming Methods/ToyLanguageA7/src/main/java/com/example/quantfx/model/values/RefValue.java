package com.example.quantfx.model.values;
import com.example.quantfx.model.types.RefType;
import com.example.quantfx.model.types.Type;

public class RefValue extends Value
{
    public int address;
    public Type locationType;

    public RefValue()
    {
        address = -1;
        locationType = new RefType();
    }

    public RefValue(Type locationType)
    {
        address = 0;
        this.locationType = locationType;
    }

    public RefValue(int address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress()
    {
        return address;
    }

    @Override
    public Type getType()
    {
        return new RefType(locationType);
    }

    @Override
    public boolean equals(Object another)
    {
        return another instanceof RefValue;
    }

    @Override
    public String toString()
    {
        return "(" + address + "," + locationType.toString() + ")";
    }
}
