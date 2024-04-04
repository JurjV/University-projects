package adt.values;

import adt.types.IType;
import adt.types.RefType;

import java.sql.Ref;

public class RefValue implements Value {
    private final int address;
    private final IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public RefValue() {
        this.address = 0;
        this.locationType = null;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", address, locationType);
    }
}
