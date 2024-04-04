package adt.statements;

import adt.structures.MyIDictionary;
import adt.structures.MyIStack;
import adt.structures.PrgState;
import adt.expressions.IExp;
import adt.types.BoolType;
import adt.values.BoolValue;
import adt.values.Value;
import controller.MyException;

public class IfStmt implements IStmt {
    IExp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(IExp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public String toString() {
        return "if(" + exp.toString() + ")then(" + thenS.toString() + ")else(" + elseS.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        MyIDictionary<String, Value> symTbl=state.getSymTable();
        Value boolVal=exp.eval(symTbl, state.getHeap());
        if(boolVal.getType().equals(new BoolType())){
            MyIStack<IStmt> stack= state.getExeStack();
            BoolValue boolExpVal=(BoolValue) boolVal;
            if(boolExpVal.getVal())
                stack.push(thenS);
            else
                stack.push(elseS);
            return state;
        }
        throw new MyException("expression of boolean type");
    }
}
