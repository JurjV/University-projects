package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.Expression;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.model.types.Type;

public class PrintStatement implements Statement
{
    Expression expression;

    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        state.getOutput().add(expression.evaluate(state.getSymbolTable(), state.getHeap()));
        return null;
    }

    public PrintStatement(Expression expression)
    {
        this.expression = expression;
    }

    // region TCS
    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public Statement deepCopy()
    {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "print(" + expression.toString() + ")";
    }
    // endregion
}
