package adt.expressions;

import adt.structures.MyIDictionary;
import adt.structures.MyIHeap;
import adt.values.Value;
import controller.MyException;

public interface IExp {
    Value eval(MyIDictionary<String,Value> tbl, MyIHeap heap) throws MyException;

}
