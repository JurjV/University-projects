package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.*;
import com.example.quantfx.model.types.BoolType;
import com.example.quantfx.model.types.IntType;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.IntValue;
import com.example.quantfx.model.values.RelationOperator;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.Heap;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.program.Stack;

public class ForStatement implements Statement
{
    private final String var;
    private final Expression assignExpression;
    private final Expression whileCondition; // < condition | less the implicit
    private final Expression incrementExpression;
    private final Statement forBody;

    public ForStatement(String var, Expression initialValueExpression, Expression maxValueExpression, Expression incrementExpression, Statement forBody)
    {
        this.var = var;
        this.assignExpression = initialValueExpression;
        this.whileCondition = maxValueExpression;
        this.incrementExpression = incrementExpression;
        this.forBody = forBody;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        Dictionary<String, Value> symTable = state.getSymbolTable();
        Stack<Statement> executionStack = state.getStack();
        Heap<Integer, Value> heap = state.getHeap();

        Value assignValue = this.assignExpression.evaluate(symTable, heap);
        Value maxValue = this.whileCondition.evaluate(symTable, heap);

        // Must be an int
        if (!maxValue.getType().equals(new IntType()))
            throw new GenericException("For statement: The max range of the FOR statement is not an int!");

        // Must be an int
        if (!assignValue.getType().equals(new IntType()))
            throw new GenericException("For statement: The assign of the FOR statement is not an int!");

        int assign = ((IntValue) assignValue).getValue();
        int max = ((IntValue) maxValue).getValue();
        DeclarationStatement declarationStatement = new DeclarationStatement(this.var, new IntType());
        AssignmentStatement assignmentStatement = new AssignmentStatement(this.var, new ValueExpression(new IntValue(assign)));
        WhileStatement whileStatement = new WhileStatement(
                new RelationalExpression(new VariableExpression(var), new ValueExpression(new IntValue(max)), RelationOperator.LESS_THAN),
                new CompoundStatement(
                        forBody,
                        new AssignmentStatement(var, incrementExpression)
                ));

        executionStack.push(whileStatement);
        executionStack.push(assignmentStatement);
        executionStack.push(declarationStatement);

        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new ForStatement(this.var, this.assignExpression.deepCopy(), this.whileCondition.deepCopy(), this.incrementExpression.deepCopy(), this.forBody.deepCopy());
    }

    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        // all expressions must be int
        Type conditionType = this.whileCondition.typeCheck(typeEnv);
        if (!conditionType.equals(new BoolType()))
            throw new GenericException("For statement: The condition of the FOR statement is not an int!");

        this.forBody.typeCheck(typeEnv.deepCopy());

        return typeEnv;
    }

    @Override
    public String toString()
    {
        return "for (" + var + assignExpression.toString() + ";" + var + "<" + whileCondition.toString() + ";" + var + "=" + assignExpression.toString();
    }
}
