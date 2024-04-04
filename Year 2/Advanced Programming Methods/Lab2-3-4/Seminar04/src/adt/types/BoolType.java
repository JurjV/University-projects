package adt.types;
import adt.values.*;

public class BoolType implements IType {
    @Override
    public boolean equals(IType another) {
        return another instanceof BoolType;
    }

    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
