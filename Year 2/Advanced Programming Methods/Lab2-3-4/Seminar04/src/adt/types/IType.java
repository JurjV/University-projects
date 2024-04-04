package adt.types;
import adt.values.Value;

public interface IType {
    boolean equals(IType another);

    Value defaultValue();
}
