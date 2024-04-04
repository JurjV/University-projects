package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.Expression;
import com.example.quantfx.model.types.BoolType;
import com.example.quantfx.model.values.BoolValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.Heap;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.program.Stack;

public class IfStatement implements Statement
{
    Expression expression;
    Statement thenStatement;
    Statement elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement)
    {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        Stack<Statement> stack = state.getStack();
        Heap<Integer, Value> heap = state.getHeap();
        Dictionary<String, Value> symbolTable = state.getSymbolTable();

        Value value = expression.evaluate(symbolTable, heap);

        if(!value.getType().equals(new BoolType()))
            throw new GenericException("If statement: The condition of the IF statement is not a boolean!");

        BoolValue boolValue = (BoolValue) value;
        boolean condition = boolValue.getValue();

        if(condition)
            stack.push(thenStatement);
        else
            stack.push(elseStatement);

        return null;
    }

    // region TCS
    @Override
    public String toString()
    {
        return "if(" + expression.toString() + ") then(" + thenStatement.toString() + ") else(" + elseStatement.toString() + ")";
    }

    @Override
    public Statement deepCopy()
    {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        if(!expression.typeCheck(typeEnv).equals(new BoolType()))
            throw new GenericException("If statement: The condition of the IF statement is not a boolean!");

        thenStatement.typeCheck(typeEnv.deepCopy());
        elseStatement.typeCheck(typeEnv.deepCopy());

        return typeEnv;
    }
    // endregion
}
