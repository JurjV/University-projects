package adt.values;

import adt.types.BoolType;
import adt.types.IType;

public class BoolValue implements Value {
    boolean val;

    public BoolValue(boolean v) {
        val = v;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val ? "True" : "False";
    }

    public IType getType() {
        return new BoolType();
    }

    public boolean equals(Value anotherValue) {
        if (!(anotherValue instanceof BoolValue))
            return false;
        BoolValue castValue = (BoolValue) anotherValue;
        return this.val == castValue.val;
    }
}
