package com.example.quantfx.model.statements;


import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.Expression;
import com.example.quantfx.model.types.RefType;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.RefValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.Heap;
import com.example.quantfx.program.ProgramState;

public class HeapWritingStatement implements Statement {

    String varName;
    Expression expression;

    public HeapWritingStatement(String variableName, Expression expression){
        this.varName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        Dictionary<String, Value> symTable = state.getSymbolTable();
        Heap<Integer, Value> heap = state.getHeap();

        if(!symTable.containsKey(this.varName))
            throw new GenericException("The key address: " + this.varName + " is not defined in the symbolTable!\n");

        Value value = symTable.get(this.varName);

        if (value.getType().equals(new RefType()))
            throw new GenericException("The key address: " + this.varName + " is not a reference type!\n");

        RefValue refValue = (RefValue) value;
        if(!heap.containsKey(refValue.address))
            throw new GenericException("The given key address: " + refValue.address + " is not defined in the heap\n");

        Value expressionValue = this.expression.evaluate(symTable, heap);

        // Logic | Update the heap with the new value
        heap.update(refValue.address, expressionValue);

        return null;
    }

    // region TCS
    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException {
        return typeEnv;
    }

    @Override
    public String toString(){
        String representation = "";
        representation += ("writeHeap(" + this.varName + ", " + this.expression.toString() + ");\n");
        return representation;
    }

    @Override
    public Statement deepCopy() {
        return new HeapWritingStatement(this.varName, this.expression);
    }
    // endregion
}
