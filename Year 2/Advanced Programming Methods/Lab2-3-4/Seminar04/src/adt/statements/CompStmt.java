package adt.statements;

import adt.structures.MyIStack;
import adt.structures.PrgState;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt snd;

    public CompStmt(IStmt v, IStmt v1) {
        this.first = v;
        this.snd = v1;
    }



    @Override
    public String toString() {
        return first.toString() + ";" + snd.toString();
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(snd);
        stk.push(first);
        return state;
    }
}
