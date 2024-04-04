package adt.expressions;

import adt.structures.MyIDictionary;
import adt.structures.MyIHeap;
import adt.values.Value;
import controller.MyException;

public class VarExp implements IExp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws MyException {
        return tbl.lookUp(id);
    }
}
