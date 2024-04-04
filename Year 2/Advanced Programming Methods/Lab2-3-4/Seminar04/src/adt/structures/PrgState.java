package adt.structures;

import adt.statements.IStmt;
import adt.values.Value;
import controller.MyException;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;

    MyIDictionary<String, BufferedReader> fileTable;
    MyIList<Value> out;

    MyIHeap heap;


    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value>
            ot, IStmt prg, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        this.heap = heap;
        this.fileTable = fileTable;
        stk.push(prg);
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public String toStringExeStack() {
        StringBuilder exeStackString = new StringBuilder();
        List<IStmt> stack = exeStack.getReversed();
        for (IStmt stmt : stack) {
            exeStackString.append(stmt.toString()).append("\n");
        }
        return exeStackString.toString();
    }

    public String toStringSymTable() throws MyException {
        StringBuilder symTableString = new StringBuilder();
        for (String key : symTable.keySet()) {
            symTableString.append(String.format("%s -> %s\n", key, symTable.lookUp(key).toString()));
        }
        return symTableString.toString();
    }

    public String toStringHeap() throws MyException {
        StringBuilder heapString = new StringBuilder();
        for (int key : heap.keySet()) {
            heapString.append(String.format("%d -> %s\n", key, heap.get(key)));
        }
        return heapString.toString();
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap newHeap) {
        this.heap = newHeap;
    }

    public String toStringOut() {
        StringBuilder outString = new StringBuilder();
        for (Value el : out.getOutput()) {
            outString.append(String.format("%s\n", el.toString()));
        }
        return outString.toString();
    }

    public String toStringFileTable() {
        StringBuilder fileTableString = new StringBuilder();
        for (String key : fileTable.keySet()) {
            fileTableString.append(String.format("%s\n", key));
        }
        return fileTableString.toString();
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> newFileTable) {
        this.fileTable = newFileTable;
    }

    @Override
    public String toString() {
        return "Execution stack: \n" + exeStack.getReversed() + "\nSymbol table: \n" + symTable.toString() + "\nOutput list: \n" + out.toString() + "\nFile table: \n" + fileTable.toString() + "\nHeap memory:" + heap.toString() + "\n";
    }

    public String programStateTOString() throws MyException {
        return "Execution stack: \n" + toStringExeStack() + "Symbol table: \n" +
                toStringSymTable() + "Output list: \n" + toStringOut() + "File table: \n" + toStringFileTable() + "Heap memory: \n" + toStringHeap();
    }
}
