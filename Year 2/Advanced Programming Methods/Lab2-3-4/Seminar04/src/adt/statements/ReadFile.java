package adt.statements;

import adt.expressions.IExp;
import adt.structures.MyIDictionary;
import adt.structures.PrgState;
import adt.types.IntType;
import adt.types.StringType;
import adt.values.IntValue;
import adt.values.StringValue;
import adt.values.Value;
import controller.MyException;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    private final IExp exp;
    private final String name;

    public ReadFile(IExp exp, String name) {
        this.exp = exp;
        this.name = name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();

        if (symTable.isDefined(name)) {
            Value val = symTable.lookUp(name);
            if (val.getType().equals(new IntType())) {
                val = exp.eval(symTable, state.getHeap());
                if (val.getType().equals(new StringType())) {
                    StringValue castValue = (StringValue) val;
                    if (fileTable.isDefined(castValue.getVal())) {
                        BufferedReader br = fileTable.lookUp(castValue.getVal());
                        try {
                            String line = br.readLine();
                            if (line == null)
                                line = "0";
                            symTable.put(name, new IntValue(Integer.parseInt(line)));
                        } catch (IOException e) {
                            throw new MyException("Could not read from file " + castValue);
                        }
                    } else {
                        throw new MyException("The file table does not contain " + castValue);
                    }
                } else {
                    throw new MyException(val + " does not evaluate to StringType");
                }
            } else {
                throw new MyException(val + " is not of type IntType");
            }
        } else {
            throw new MyException(name + " is not present in the symTable.");
        }
        return state;
    }

    @Override
    public String toString() {
        return "ReadFile(" + exp.toString() + "," + name + ")";
    }

}
