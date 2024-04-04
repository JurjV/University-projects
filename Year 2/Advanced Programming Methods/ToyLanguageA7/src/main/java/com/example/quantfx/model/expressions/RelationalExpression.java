package com.example.quantfx.model.expressions;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.types.BoolType;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.BoolValue;
import com.example.quantfx.model.values.RelationOperator;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.Heap;

public class RelationalExpression implements Expression
{
    private Expression leftExpression;
    private Expression rightExpression;
    private RelationOperator operator;

    public RelationalExpression(Expression leftExpression, Expression rightExpression, RelationOperator operator)
    {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public Type typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        Type leftType, rightType;

        leftType = leftExpression.typeCheck(typeEnv);
        rightType = rightExpression.typeCheck(typeEnv);

        if(!leftType.equals(rightType))
            throw new GenericException("Relational expression operands have different types!");

        return new BoolType();
    }

    @Override
    public Expression deepCopy()
    {
        return new RelationalExpression(leftExpression.deepCopy(), rightExpression.deepCopy(), operator);
    }

    @Override
    public Value evaluate(Dictionary<String, Value> table, Heap<Integer, Value> heap) throws GenericException
    {
        Value leftValue, rightValue;

        leftValue = leftExpression.evaluate(table, heap);
        rightValue = rightExpression.evaluate(table, heap);

        return new BoolValue(leftValue.compare(rightValue, operator));
    }

    @Override
    public String toString()
    {
        return leftExpression.toString() + " " + operator.toString() + " " + rightExpression.toString();
    }

    public Expression getLeftExpression()
    {
        return leftExpression;
    }
}
