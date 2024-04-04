package adt.statements;

import adt.structures.MyIDictionary;
import adt.structures.MyIList;
import adt.structures.PrgState;
import adt.expressions.IExp;
import adt.values.Value;
import controller.MyException;

public class PrintStmt implements IStmt {
    IExp exp;

    public PrintStmt(IExp v) {
        this.exp = v;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> list = state.getOut();
        MyIDictionary<String,Value> symTbl=state.getSymTable();
        list.add(exp.eval(symTbl, state.getHeap()));
        return state;
    }
}
