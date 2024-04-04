package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.Expression;
import com.example.quantfx.model.types.StringType;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.StringValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.ProgramState;

import java.io.BufferedReader;

public class CloseReadFileStatement implements Statement
{
    Expression fileNameExpression;

    public CloseReadFileStatement(Expression fileNameExpression)
    {
        this.fileNameExpression = fileNameExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException
    {
        Value fileNameValue = fileNameExpression.evaluate(state.getSymbolTable(), state.getHeap());

        // fileNameValue should be a string
        if(!fileNameValue.getType().equals(new StringType()))
            throw new GenericException("File name is not a string.");

        String fileName = ((StringValue) fileNameValue).getValue();

        // The file should be opened, it should exist in the file table
        if(!state.getFileTable().containsKey(fileName))
            throw new GenericException("File " + fileName + " does not exist.");

        // Logic | Remove the file from the file table
        try
        {
            // Close the actual file
            BufferedReader fileDescriptor = state.getFileTable().get(fileName);
            fileDescriptor.close();
            state.getFileTable().remove(fileName);
        }
        catch (Exception exception)
        {
            throw new GenericException("Could not close file " + fileName + ".");
        }

        return null;
    }


    // region TCS
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
        return "closeRFile(" + fileNameExpression.toString() + ")";
    }

    // endregion
}
