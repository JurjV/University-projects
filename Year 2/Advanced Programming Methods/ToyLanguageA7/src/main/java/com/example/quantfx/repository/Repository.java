package com.example.quantfx.repository;

import com.example.quantfx.program.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

// Holds reference to program states
public class Repository implements GenericRepository
{
    private List<ProgramState> states;
    private String logFilePath;

    public Repository(ProgramState state, String logFilePath)
    {
        states = new LinkedList<ProgramState>();
        states.add(state);
        this.logFilePath = logFilePath;

        // Clear log file
        try
        {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void logExecution(ProgramState state)
    {
        try
        {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

            logFile.println("Execution Stack:");
            logFile.println(state.getStack().toString());

            logFile.println("Symbol Table:");
            logFile.println(state.getSymbolTable().toString());

            logFile.println("File Table:");
            logFile.println(state.getFileTable().toKeyString());

            logFile.println("Output:");
            logFile.println(state.getOutput().toString());

            logFile.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void clear()
    {
        states = new LinkedList<ProgramState>();
    }

    public void addProgram(ProgramState program)
    {
        states.add(program);
    }

    public void addPrograms(List<ProgramState> programs)
    {
        states = programs;
    }

    public ProgramState getCurrent()
    {
        return states.get(0);
    }

    // Why would this have to be in the interface???
    public List<ProgramState> getPrograms()
    {
        return states;
    }

    public void setPrograms(List<ProgramState> programs)
    {
        states = programs;
    }
}
