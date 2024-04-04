package adt.values;

import adt.types.IType;
import adt.types.IntType;

public class IntValue implements Value {
    int val;

    public IntValue(int v) {
        val = v;
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "" + val;
    }

    public IType getType() {
        return new IntType();
    }

    public boolean equals(Value anotherValue) {
        if (!(anotherValue instanceof IntValue))
            return false;
        IntValue castValue = (IntValue) anotherValue;
        return this.val == castValue.val;
    }
}
