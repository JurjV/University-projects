package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.Expression;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.*;

public class AssignmentStatement implements Statement
{
    String varName;
    Expression expression;

    public AssignmentStatement(String varName, Expression expression)
    {
        this.varName = varName;
        this.expression = expression;
    }

    // Execution
    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        Dictionary<String, Value> symbolTable = state.getSymbolTable();

        if(!symbolTable.containsKey(varName)) // Check if the variable is defined in the symbol table
            throw new GenericException("Variable " + varName + " is not defined.");

        Heap<Integer, Value> heap = state.getHeap();
        Value value = expression.evaluate(symbolTable, heap);
        Type type = (symbolTable.get(varName)).getType();

        if(!value.getType().equals(type)) // Check if the types of the variable and the value match
            throw new GenericException("Declared type of variable " + varName + " and type of the assigned expression do not match.");

        // Logic | Update the symbol table
        symbolTable.update(varName, value);

        return null;
    }

    // region TCS
    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        Type typeVar = typeEnv.get(varName);
        Type typeExp = expression.typeCheck(typeEnv);

        if(!typeVar.equals(typeExp))
            throw new GenericException("Declared type of variable " + varName + " and type of the assigned expression do not match.");

        return typeEnv;
    }

    @Override
    public Statement deepCopy()
    {
        return new AssignmentStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return varName + " = " + expression.toString();
    }

    // endregion
}
