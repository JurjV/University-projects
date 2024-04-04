package adt.statements;

import adt.expressions.IExp;
import adt.structures.MyIDictionary;
import adt.structures.MyIHeap;
import adt.structures.PrgState;
import adt.types.IType;
import adt.types.RefType;
import adt.values.RefValue;
import adt.values.Value;
import controller.MyException;

public class NewStmt implements IStmt {
    private final String varName;
    private final IExp expression;

    public NewStmt(String varName, IExp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (symTable.isDefined(varName)) {
            Value varValue = symTable.lookUp(varName);
            if ((varValue.getType() instanceof RefType)) {
                Value evaluated = expression.eval(symTable, heap);
                IType locationType = ((RefValue) varValue).getLocationType();
                if (locationType.equals(evaluated.getType())) {
                    int newPosition = heap.add(evaluated);
                    symTable.put(varName, new RefValue(newPosition, locationType));
                    state.setSymTable(symTable);
                    state.setHeap(heap);
                } else
                    throw new MyException(String.format("%s not of %s", varName, evaluated.getType()));
            } else {
                throw new MyException(String.format("%s in not of RefType", varName));
            }
        } else {
            throw new MyException(String.format("%s not in symTable", varName));
        }
        return null;
    }


    @Override
    public String toString() {
        return String.format("New(%s, %s)", varName, expression);
    }
}
