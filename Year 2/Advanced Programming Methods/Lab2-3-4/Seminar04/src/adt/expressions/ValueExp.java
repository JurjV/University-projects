package adt.expressions;

import adt.structures.MyIDictionary;
import adt.structures.MyIHeap;
import adt.values.Value;
import controller.MyException;

public class ValueExp implements IExp {
    Value value;

    public ValueExp(Value value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return value.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        return value;
    }
}
