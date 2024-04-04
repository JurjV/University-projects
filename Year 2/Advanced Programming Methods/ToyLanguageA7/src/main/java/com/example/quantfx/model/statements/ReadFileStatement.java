package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.Expression;
import com.example.quantfx.model.types.IntType;
import com.example.quantfx.model.types.StringType;
import com.example.quantfx.model.values.IntValue;
import com.example.quantfx.model.values.StringValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.model.types.Type;

import java.io.BufferedReader;

public class ReadFileStatement implements Statement
{
    Expression expression;
    String variableName;

    public ReadFileStatement(Expression expression, String variableName)
    {
        this.expression = expression;
        this.variableName = variableName;
    }

    // Execution
    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        // Check if variable is declared
        if(!state.getSymbolTable().containsKey(variableName))
            throw new GenericException("Variable " + variableName + " not declared");

        // Check if int type
        Value value = state.getSymbolTable().get(variableName);
        if(!value.getType().equals(new IntType()))
            throw new GenericException("Variable " + variableName + " is not of type int");

        // Check if file descriptor exists
        Value expressionValue = expression.evaluate(state.getSymbolTable(), state.getHeap());
        if(!expressionValue.getType().equals(new StringType()))
            throw new GenericException("Expression " + expression.toString() + " is not of type string");

        String fileName = ((StringValue)expressionValue).getValue();

        if(!state.getFileTable().containsKey(fileName))
            throw new GenericException("File " + fileName + " not declared");

        // Logic | Update the int variable with the value read from the file
        try
        {
            BufferedReader bufferedReader = state.getFileTable().get(fileName);
            String line = bufferedReader.readLine();
            int valueRead = 0;
            if(line != null)
                valueRead = Integer.parseInt(line);

            // Update the variable
            state.getSymbolTable().update(variableName, new IntValue(valueRead));
        }
        catch(Exception exception)
        {
            throw new GenericException(exception.getMessage());
        }

        return null;
    }

    // region TCS
    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        Type typeVariable = typeEnv.get(variableName);
        Type typeExpression = expression.typeCheck(typeEnv);

        if(!typeVariable.equals(new IntType()))
            throw new GenericException("Variable " + variableName + " is not of type int");

        if(!typeExpression.equals(new StringType()))
            throw new GenericException("Expression " + expression.toString() + " is not of type string");

        return typeEnv;
    }

    @Override
    public Statement deepCopy()
    {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }

    @Override
    public String toString()
    {
        return "readFile(" + expression.toString() + ", " + variableName + ")";
    }
    // endregion
}
