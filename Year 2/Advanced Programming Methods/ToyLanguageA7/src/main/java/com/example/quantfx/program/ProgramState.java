package com.example.quantfx.program;
import com.example.quantfx.model.statements.Statement;
import com.example.quantfx.model.values.IntValue;
import com.example.quantfx.model.values.RefValue;
import com.example.quantfx.model.values.StringValue;
import com.example.quantfx.model.values.Value;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ProgramState
{
    private Stack<Statement> executionStack;
    private Dictionary<String, Value> symbolTable;
    private Dictionary<String, BufferedReader> fileTable;
    private Heap<Integer, Value> heap;
    private List<Value> output;
    private int id;
    private static int nextID = 0;
    Statement originalProgram;

    public ProgramState(Stack<Statement> executionStack,
                        Dictionary<String, Value> symbolTable,
                        Dictionary<String, BufferedReader> fileTable,
                        Heap<Integer, Value> heap,
                        List<Value> output,
                        Statement originalProgram,
                        int id)
    {
        this.id = id;
        this.fileTable = fileTable;
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.heap = heap;
        this.output = output;
        this.originalProgram = originalProgram.deepCopy();
        executionStack.push(originalProgram);
    }

    public ProgramState(Statement originalProgram)
    {
        this(new Stack<>(), new Dictionary<>(), new Dictionary<>(), new Heap<>(), new List<>(), originalProgram, generateNewID());
    }

    public static synchronized int generateNewID()
    {
        return nextID++;
    }

    public boolean isNotCompleted()
    {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStep() throws Exception
    {
        if (executionStack.isEmpty())
            throw new Exception("Program state stack is empty");

        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    // region Getters

    public int getId()
    {
        return id;
    }

    public Stack<Statement> getStack()
    {
        return executionStack;
    }

    public java.util.List<String> getStackInstruction()
    {
        java.util.List<String> result = new java.util.ArrayList<>();

        for (Statement statement : executionStack.getContents())
            result.add(0, statement.toString());

        return result;
    }

    public  Heap<Integer, Value> getHeap()
    {
        return heap;
    }

    public Dictionary<String, Value> getSymbolTable()
    {
        return symbolTable;
    }

    public List<Value> getOutput()
    {
        return output;
    }

    public Dictionary<String, BufferedReader> getFileTable()
    {
        return fileTable;
    }
    // endregion

    @Override
    public String toString()
    {
        return "Id: " + id + "\nExecution Stack:\n" + executionStack.toString() + "\nSymbol Table:\n"  + symbolTable.toString()  + "File Table:\n" + fileTable.toKeyString()+ "\nOutput:\n" + output.toString() + "\n";
    }


    public ProgramState deepCopy()
    {
        return new ProgramState(
                executionStack.deepCopy(),
                symbolTable.deepCopy(),
                fileTable.deepCopy(),
                heap.deepCopy(),
                output.deepCopy(),
                originalProgram.deepCopy(),
                generateNewID());
    }
}
