package adt.expressions;

import adt.structures.MyIDictionary;
import adt.structures.MyIHeap;
import adt.types.BoolType;
import adt.types.IntType;
import adt.values.BoolValue;
import adt.values.IntValue;
import adt.values.Value;
import controller.MyException;

public class RelationalExp implements IExp {
    IExp e1;
    IExp e2;
    String op;

    public RelationalExp(String op, IExp e1, IExp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString() {
        return this.e1.toString() + " " + this.op + " " + this.e2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        Value v1, v2;

        v1 = e1.eval(tbl,heap);
        if (!v1.getType().equals(new IntType()))
            throw new MyException("Left value not of int type!");

        v2 = e2.eval(tbl,heap);
        if (!v2.getType().equals(new IntType()))
            throw new MyException("Right value not of int type!");

        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;

        int leftInt = i1.getVal();
        int rightInt = i2.getVal();

        return switch (op) {
            case "<" -> new BoolValue(leftInt < rightInt);
            case ">" -> new BoolValue(leftInt > rightInt);
            case "<=" -> new BoolValue(leftInt <= rightInt);
            case ">=" -> new BoolValue(leftInt >= rightInt);
            case "==" -> new BoolValue(leftInt == rightInt);
            case "!=" -> new BoolValue(leftInt != rightInt);
            default -> null;
        };
    }
}
