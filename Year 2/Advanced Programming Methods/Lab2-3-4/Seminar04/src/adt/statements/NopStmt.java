package adt.statements;

import adt.structures.PrgState;

public class NopStmt implements IStmt {
    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public PrgState execute(PrgState state) {
        return state;
    }
}
