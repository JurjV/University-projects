package com.example.quantfx.model.expressions;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.types.BoolType;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.BoolValue;
import com.example.quantfx.model.values.LogicalOperator;
import com.example.quantfx.model.values.RelationOperator;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.GenericDictionary;
import com.example.quantfx.program.Heap;

public class LogicExpression implements Expression
{
    Expression leftExpression;
    Expression rightExpression;

    LogicalOperator operator;

    public LogicExpression(Expression leftExpression, Expression rightExpression, LogicalOperator operator)
    {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public Expression deepCopy()
    {
        return new LogicExpression(leftExpression.deepCopy(), rightExpression.deepCopy(), operator);
    }

    @Override
    public Value evaluate(Dictionary<String, Value> table, Heap<Integer, Value> heap) throws GenericException
    {
        Value leftValue, rightValue;

        leftValue = leftExpression.evaluate(table, heap);
        if(!leftValue.getType().equals(new BoolType()))
            throw new GenericException("Left value not of Bool type!");

        rightValue = rightExpression.evaluate(table, heap);
        if(!rightValue.getType().equals(new BoolType()))
            throw new GenericException("Right value not of Bool type!");

        return leftValue.relate(rightValue, operator);
    }

    @Override
    public Type typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        Type leftType, rightType;

        leftType = leftExpression.typeCheck(typeEnv);
        rightType = rightExpression.typeCheck(typeEnv);

        if(!leftType.equals(new BoolType()))
            throw new GenericException("Left type not of Bool type!");

        if(!rightType.equals(new BoolType()))
            throw new GenericException("Right type not of Bool type!");

        return new BoolType();
    }

    @Override
    public String toString()
    {
        return leftExpression.toString() + " " + operator.toString() + " " + rightExpression.toString();
    }
}
