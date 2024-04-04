package adt.types;

import adt.values.*;

public class IntType implements IType {

    @Override
    public boolean equals(IType another) {
        return another instanceof IntType;
    }

    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
