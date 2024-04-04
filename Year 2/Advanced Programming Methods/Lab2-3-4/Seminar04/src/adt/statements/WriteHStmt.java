package adt.statements;

import adt.expressions.IExp;
import adt.structures.MyIDictionary;
import adt.structures.MyIHeap;
import adt.structures.PrgState;
import adt.values.RefValue;
import adt.values.Value;
import controller.MyException;

public class WriteHStmt implements IStmt{
    private final String varName;
    private final IExp exp;

    public WriteHStmt(String varName, IExp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (!symTable.isDefined(varName))
            throw new MyException(String.format("%s not present in the symTable", varName));
        Value value = symTable.lookUp(varName);
        if (!(value instanceof RefValue))
            throw new MyException(String.format("%s not of RefType", value));
        RefValue refValue = (RefValue) value;
        Value evaluated = exp.eval(symTable, heap);
        if (!evaluated.getType().equals(refValue.getLocationType()))
            throw new MyException(String.format("%s not of %s", evaluated, refValue.getLocationType()));
        heap.update(refValue.getAddress(), evaluated);
        state.setHeap(heap);
        return state;
    }


    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, exp);
    }
}
