package adt.statements;

import adt.expressions.IExp;
import adt.structures.MyIDictionary;
import adt.structures.PrgState;
import adt.types.StringType;
import adt.values.StringValue;
import adt.values.Value;
import controller.MyException;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private final IExp exp;

    public CloseRFile(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value value = exp.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new MyException(exp + " does not evaluate to StringValue");
        StringValue fileName = (StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName.getVal()))
            throw new MyException(value + " is not present in the FileTable");
        BufferedReader br = fileTable.lookUp(fileName.getVal());
        try {
            br.close();
        } catch (IOException e) {
            throw new MyException("Unexpected error in closing " + value);
        }
        fileTable.remove(fileName.getVal());
        state.setFileTable(fileTable);
        return state;
    }

    @Override
    public String toString() {
        return "CloseRFile(" + exp.toString() + ")";
    }
}
