package adt.statements;

import adt.structures.PrgState;
import controller.MyException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
}
