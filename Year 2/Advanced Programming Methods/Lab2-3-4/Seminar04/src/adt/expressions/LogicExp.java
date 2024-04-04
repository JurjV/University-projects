package adt.expressions;

import adt.structures.MyIHeap;
import adt.types.BoolType;
import adt.values.BoolValue;
import adt.structures.MyIDictionary;
import adt.values.Value;
import controller.MyException;

public class LogicExp implements IExp {
    IExp e1;
    IExp e2;
    String op;

    public LogicExp(String op, IExp e1, IExp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public String toString() {
        return e1.toString() + op + e2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        Value v1, v2;

        v1 = e1.eval(tbl,heap);
        if (!v1.getType().equals(new BoolType()))
            throw new MyException("Left value not of boolean type!");

        v2 = e2.eval(tbl,heap);
        if (!v2.getType().equals(new BoolType()))
            throw new MyException("Right value not of boolean type!");

        BoolValue b1 = (BoolValue) v1;
        BoolValue b2 = (BoolValue) v2;

        boolean leftBool=b1.getVal();
        boolean rightBool=b2.getVal();

        return switch (op) {
            case "&&" -> new BoolValue(leftBool && rightBool);
            case "||" -> new BoolValue(leftBool || rightBool);
            case "==" -> new BoolValue(leftBool == rightBool);
            case "!=" -> new BoolValue(leftBool != rightBool);
            default -> null;
        };
    }
}
