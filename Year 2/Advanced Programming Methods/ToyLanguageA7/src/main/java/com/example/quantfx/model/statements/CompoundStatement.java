package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.GenericStack;
import com.example.quantfx.program.ProgramState;

public class CompoundStatement implements Statement
{
    Statement leftStatement;
    Statement rightStatement;

    // Execution
    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        // We push the right statement first, because we want to execute it first
        // (the left statement will be executed after the right one)
        GenericStack<Statement> statementsStack = state.getStack();
        statementsStack.push(rightStatement);
        statementsStack.push(leftStatement);
        return null;
    }

    public CompoundStatement(Statement leftStatement, Statement rightStatement)
    {
        this.leftStatement = leftStatement;
        this.rightStatement = rightStatement;
    }

    // region TSD

    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        return rightStatement.typeCheck(leftStatement.typeCheck(typeEnv));
    }

    @Override
    public String toString()
    {
        return "(" + leftStatement.toString() + ";" + rightStatement.toString() + ")";
    }

    @Override
    public Statement deepCopy()
    {
        return new CompoundStatement(leftStatement.deepCopy(), rightStatement.deepCopy());
    }

    // endregion
}
