package adt.types;
import adt.values.*;

public class StringType implements IType {
    @Override
    public boolean equals(IType another) {
        return another instanceof StringType;
    }

    public String toString() {
        return "string";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}