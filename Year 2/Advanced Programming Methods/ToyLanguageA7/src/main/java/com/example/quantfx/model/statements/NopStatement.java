package com.example.quantfx.model.statements;

import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.model.types.Type;

public class NopStatement implements Statement
{
    @Override
    public ProgramState execute(ProgramState state)
    {
        return null;
    }

    // region TCS
    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv)
    {
        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "nop";
    }

    @Override
    public Statement deepCopy()
    {
        return new NopStatement();
    }
    // endregion
}
