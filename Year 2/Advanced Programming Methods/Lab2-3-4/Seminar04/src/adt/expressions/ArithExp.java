package adt.expressions;

import adt.structures.MyIDictionary;
import adt.structures.MyIHeap;
import adt.types.IntType;
import adt.values.IntValue;
import adt.values.Value;
import controller.MyException;

public class ArithExp implements IExp {
    IExp e1;
    IExp e2;
    String op;//   +,-*,/

    public ArithExp(String op, IExp e1, IExp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString() {
        return e1.toString() + op + e2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl,heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl,heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op.equals("+")) return new IntValue(n1 + n2);
                if (op.equals("-")) return new IntValue(n1 - n2);
                if (op.equals("*")) return new IntValue(n1 * n2);
                if (op.equals("/"))
                    if (n2 == 0) throw new MyException("division by zero");
                    else return new IntValue(n1 / n2);
                else throw new MyException("not a valid option!");
            } else throw new MyException("second operand is not an integer");
        } else throw new MyException("first operand is not an integer");
    }
}
