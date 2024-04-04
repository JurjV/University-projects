package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.Expression;
import com.example.quantfx.model.types.IntType;
import com.example.quantfx.model.types.RefType;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.IntValue;
import com.example.quantfx.model.values.RefValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.Heap;
import com.example.quantfx.program.ProgramState;

public class HeapAllocationStatement implements Statement {

    private final Expression expression;
    private final String variableName;

    public HeapAllocationStatement(String variableName, Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    // Execution
    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        Dictionary<String, Value> symTable = state.getSymbolTable();
        Heap<Integer, Value> heap = state.getHeap();

        // The variable must be defined in the symbol table
        if(!symTable.containsKey(this.variableName))
            throw new GenericException(this.variableName + " is not defined in the symbol table!\n");

        Value value = symTable.get(this.variableName);
        Type type = value.getType();

        // The variable must be a reference type
        if(!(type instanceof RefType))
            throw new GenericException(this.variableName + " is not a reference type!\n");

        RefValue refValue = (RefValue) value;
        Value expValue = this.expression.evaluate(symTable, heap);

        // The expression must match the type of the variable
        if(!(expValue.getType().equals(refValue.locationType)))
            throw new GenericException("The types of the variable and the expression do not match!\n");

        int copyAddress = heap.size() + 1;
        heap.insert(copyAddress, expValue);

        symTable.update(this.variableName, new RefValue(copyAddress, refValue.locationType));

        return null;
    }

    // region TCS

    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException {
        Type typeVariable = typeEnv.get(this.variableName);
        Type typeExpression = this.expression.typeCheck(typeEnv);
        if (typeVariable.equals(new RefType(typeExpression)))
            return typeEnv;
        else
            throw new GenericException("Heap Allocation Statement: right hand side and left hand side have different types!");
    }

    @Override
    public String toString()
    {
        String representation = "";
        representation += ("new(" + this.variableName + ", " + this.expression.toString() + ");\n");
        return representation;
    }

    @Override
    public Statement deepCopy()
    {
        return new HeapAllocationStatement(this.variableName, this.expression);
    }

    // endregion
}