package com.example.quantfx.model.expressions;

import com.example.quantfx.exceptions.DivisionByZeroException;
import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.types.IntType;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.ArithmeticOperator;
import com.example.quantfx.model.values.IntValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.GenericDictionary;
import com.example.quantfx.program.Heap;

public class ArithmeticExpression implements Expression
{
    private Expression leftExpression;
    private Expression rightExpression;
    private ArithmeticOperator operator;

    @Override
    public Value evaluate(Dictionary<String, Value> table, Heap<Integer, Value> heap) throws GenericException
    {
        Value leftValue, rightValue;

        leftValue = leftExpression.evaluate(table, heap);
        if(!leftValue.getType().equals(new IntType()))
            throw new GenericException("Left value not of Int type!");

        rightValue = rightExpression.evaluate(table, heap);
        if(!rightValue.getType().equals(new IntType()))
            throw new GenericException("Right value not of Int type!");

        IntValue leftInt = (IntValue)leftValue;
        IntValue rightInt = (IntValue)rightValue;

        return (IntValue)leftInt.perform(rightInt, operator);
    }

    public ArithmeticExpression(Expression leftExpression, Expression rightExpression, ArithmeticOperator operator)
    {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public String toString()
    {
        return leftExpression.toString() + " " + operator.toString() + " " + rightExpression.toString();
    }

    @Override
    public Type typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        Type leftType, rightType;

        leftType = leftExpression.typeCheck(typeEnv);
        rightType = rightExpression.typeCheck(typeEnv);

        if(!leftType.equals(new IntType()))
            throw new GenericException("Left expression not of Int type!");

        if(!rightType.equals(new IntType()))
            throw new GenericException("Right expression not of Int type!");

        return new IntType();
    }

    @Override
    public Expression deepCopy()
    {
        return new ArithmeticExpression(leftExpression.deepCopy(), rightExpression.deepCopy(), operator);
    }
}
