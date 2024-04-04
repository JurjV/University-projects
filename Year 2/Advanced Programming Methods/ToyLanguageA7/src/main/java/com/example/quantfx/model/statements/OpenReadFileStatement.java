package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.Expression;
import com.example.quantfx.model.values.StringValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.model.types.StringType;
import com.example.quantfx.model.types.Type;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenReadFileStatement implements Statement
{
    Expression fileNameExpression;

    public OpenReadFileStatement(Expression fileNameExpression)
    {
        this.fileNameExpression = fileNameExpression;
    }

    // Execution
    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        Value fileName = fileNameExpression.evaluate(state.getSymbolTable(), state.getHeap());

        // Check if the file name is a string
        if(!fileName.getType().equals(new StringType()))
            throw new GenericException("File name is not a string.");

        StringValue fileNameSV = (StringValue) fileName;

        // Check if the file is already opened
        if(state.getFileTable().containsKey(fileNameSV.getValue()))
            throw new GenericException("File " + fileName.toString() + " is already opened.");
        else
            state.getFileTable().add(fileNameSV.getValue(), null); // Add the file to the file table

        // Open the file
        try
        {
            BufferedReader fileDescriptor = new BufferedReader(new FileReader(fileNameSV.getValue()));
            state.getFileTable().update(fileNameSV.getValue(), fileDescriptor);
        }
        catch (Exception exception)
        {
            throw new GenericException("Could not open file " + fileName.toString() + ".");
        }

        return null;
    }

    // region TDS
    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException
    {
        Type expressionType = fileNameExpression.typeCheck(typeEnv);
        if(!expressionType.equals(new StringType()))
            throw new GenericException("File name is not a string.");
        return typeEnv;
    }

    @Override
    public Statement deepCopy()
    {
        return new OpenReadFileStatement(fileNameExpression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "openRFile(" + fileNameExpression.toString() + ")";
    }
    // endregion
}
