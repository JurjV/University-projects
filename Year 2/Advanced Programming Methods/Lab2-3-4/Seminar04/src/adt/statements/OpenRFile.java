package adt.statements;

import adt.expressions.IExp;
import adt.structures.MyIDictionary;
import adt.structures.MyIStack;
import adt.structures.PrgState;
import adt.types.StringType;
import adt.values.StringValue;
import adt.values.Value;
import controller.MyException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt {
    IExp exp;

    public OpenRFile(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value value = exp.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new StringType())) {
            StringValue fileName = (StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isDefined(fileName.getVal())) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fileName.getVal()));
                } catch (FileNotFoundException e) {
                    throw new MyException(fileName.getVal() + " could not be opened");
                }
                fileTable.put(fileName.getVal(), br);
                state.setFileTable(fileTable);
            } else {
                throw new MyException(fileName.getVal() + " is already opened");
            }
        } else {
            throw new MyException(exp.toString() + " does not evaluate to StringType");
        }
        return state;
    }

    @Override
    public String toString() {
        return "OpenRFile(" + exp.toString() + ")";
    }
}

