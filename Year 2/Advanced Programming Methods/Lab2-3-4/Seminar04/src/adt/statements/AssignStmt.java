package adt.statements;

import adt.structures.MyIDictionary;
import adt.structures.PrgState;
import adt.expressions.IExp;
import adt.types.IType;
import adt.values.Value;
import controller.MyException;

public class AssignStmt implements IStmt {
    String id;
    IExp exp;

    public AssignStmt(String v, IExp valueExp) {
        this.id = v;
        this.exp = valueExp;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
//        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl, state.getHeap());
            IType typId = (symTbl.lookUp(id)).getType();
            if (val.getType().equals(typId))
                symTbl.put(id, val);
            else
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do not match");
        } else throw new MyException("the used variable " + id + " was not declared before");
        return state;
    }
}
