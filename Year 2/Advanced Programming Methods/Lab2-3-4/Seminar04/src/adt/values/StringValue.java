package adt.values;

import adt.types.*;

public class StringValue implements Value {
    String val;

    public StringValue(String val) {
        this.val = val;
    }

    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return val;
    }

    public String getVal() {
        return val;
    }

    public boolean equals(Value anotherValue) {
        if (!(anotherValue instanceof StringValue))
            return false;
        StringValue castValue = (StringValue) anotherValue;
        return this.val.equals(castValue.val);
    }
}
