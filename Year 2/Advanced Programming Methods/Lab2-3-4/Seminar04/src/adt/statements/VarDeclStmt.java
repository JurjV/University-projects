package adt.statements;

import adt.structures.MyIDictionary;
import adt.structures.PrgState;
import adt.types.BoolType;
import adt.types.IType;
import adt.types.IntType;
import adt.values.BoolValue;
import adt.values.IntValue;
import adt.values.Value;
import controller.MyException;

public class VarDeclStmt implements IStmt {
    String name;
    IType type;

    public VarDeclStmt(String varName, IType type) {
        this.name = varName;
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if (symTbl.isDefined(name)) {
            throw new MyException("variable " + name + " is already declared");
        }
        symTbl.put(name, type.defaultValue());
        state.setSymTable(symTbl);
        return state;
    }
}
