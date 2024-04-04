package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.StringValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.Heap;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.program.Stack;

import java.io.BufferedReader;

public class ForkStatement implements Statement
{
    Statement statement;

    public ForkStatement(Statement statement)
    {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        return new ProgramState(
                new Stack<Statement>(),
                state.getSymbolTable().deepCopy(),
                state.getFileTable(),
                state.getHeap(),
                state.getOutput(),
                statement,
                ProgramState.generateNewID());
    }

    // region TCS

    @Override
    public Statement deepCopy()
    {
        return new ForkStatement(this.statement);
    }

    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "fork( " + this.statement + " );";
    }

    // endregion
}
