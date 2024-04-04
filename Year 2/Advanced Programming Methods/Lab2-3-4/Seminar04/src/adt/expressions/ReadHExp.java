package adt.expressions;

import adt.structures.MyIDictionary;
import adt.structures.MyIHeap;
import adt.types.IType;
import adt.values.RefValue;
import adt.values.Value;
import controller.MyException;

public class ReadHExp implements IExp{
    private final IExp exp;

    public ReadHExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heap) throws MyException {
        Value value = exp.eval(symTable, heap);
        if (value instanceof RefValue refValue) {
            if (heap.containsKey(refValue.getAddress()))
                try {
                    return heap.get(refValue.getAddress());
                } catch (MyException e) {
                    e.printStackTrace();
                }
            else
                throw new MyException("The address is not defined on the heap!");
        } else
            throw new MyException(String.format("%s not of RefType", value));
        return null;
    }


    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", exp);
    }
}
