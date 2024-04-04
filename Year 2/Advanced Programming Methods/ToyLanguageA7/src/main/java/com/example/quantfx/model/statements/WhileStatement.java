package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.Expression;
import com.example.quantfx.model.types.BoolType;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.BoolValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.Heap;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.program.Stack;

public class WhileStatement implements Statement
{

    private final Expression whileCondition;
    private final Statement whileBody;

    public WhileStatement(Expression whileCondition, Statement whileBody)
    {
        this.whileCondition = whileCondition;
        this.whileBody = whileBody;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        Dictionary<String, Value> symTable = state.getSymbolTable();
        Stack<Statement> executionStack = state.getStack();
        Heap<Integer, Value> heap = state.getHeap();

        Value evaluatedExpression = this.whileCondition.evaluate(symTable, heap);

        // Must be a boolean
        if (!evaluatedExpression.getType().equals(new BoolType()))
            throw new GenericException("While statement: The condition of the WHILE statement is not a boolean!");

        BoolValue boolValue = (BoolValue) evaluatedExpression;
        boolean condition = boolValue.getValue();
        if (condition)
        {
            executionStack.push(new WhileStatement(this.whileCondition, this.whileBody));
            executionStack.push(this.whileBody);
        }

        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new WhileStatement(this.whileCondition, this.whileBody);
    }

    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        Type typeExpression = this.whileCondition.typeCheck(typeEnv);
        if (!typeExpression.equals(new BoolType()))
            throw new GenericException("While statement: The condition of the WHILE statement is not a boolean!");

        this.whileBody.typeCheck(typeEnv.deepCopy());

        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "while(" + whileCondition.toString() + "){ " + whileBody.toString() + " }";
    }

}
