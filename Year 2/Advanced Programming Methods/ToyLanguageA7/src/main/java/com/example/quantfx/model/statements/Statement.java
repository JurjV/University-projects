package com.example.quantfx.model.statements;
import com.example.quantfx.exceptions.CollectionException;
import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.GenericDictionary;
import com.example.quantfx.program.ProgramState;

public interface Statement
{
    ProgramState execute(ProgramState state) throws GenericException;
    Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException;

    Statement deepCopy();
}
