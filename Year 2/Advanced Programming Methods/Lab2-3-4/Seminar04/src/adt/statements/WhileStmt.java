package adt.statements;

import adt.expressions.IExp;
import adt.structures.MyIStack;
import adt.structures.PrgState;
import adt.types.BoolType;
import adt.values.BoolValue;
import adt.values.Value;
import controller.MyException;

public class WhileStmt implements IStmt {

    IExp exp;
    IStmt statement;

    public WhileStmt(IExp exp, IStmt statement) {
        this.exp = exp;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value value = exp.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStmt> stack = state.getExeStack();
        if (!value.getType().equals(new BoolType()))
            throw new MyException(String.format("%s is not of BoolType", value));
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getVal()) {
            stack.push(this);
            stack.push(statement);
        }
        return state;
    }


    @Override
    public String toString() {
        return String.format("while(%s){%s}", exp, statement);
    }
}
