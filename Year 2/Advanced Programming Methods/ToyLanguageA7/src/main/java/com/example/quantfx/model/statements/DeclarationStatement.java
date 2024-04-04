package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.CollectionException;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.program.Stack;

public class DeclarationStatement implements Statement
{
    public String variableName;
    public Type type;

    public DeclarationStatement(String variableName, Type type)
    {
        this.variableName = variableName;
        this.type = type;
    }

    // Execution | We add the variable to the symbol table
    @Override
    public ProgramState execute(ProgramState state) throws CollectionException
    {
        if(state.getSymbolTable().containsKey(variableName))
            throw new CollectionException("Variable " + variableName + " already declared.");

        // Logic | Add the variable to the symbol table
        state.getSymbolTable().add(variableName, type.defaultValue());
        return null;
    }

    // region TSD
    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws CollectionException
    {
        typeEnv.add(variableName, type);
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return type.toString() + " " + variableName;
    }

    @Override
    public Statement deepCopy()
    {
        return new DeclarationStatement(variableName, type);
    }
    // endregion
}
