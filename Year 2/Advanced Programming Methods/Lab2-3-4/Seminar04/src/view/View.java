package view;

import adt.expressions.ArithExp;
import adt.expressions.ValueExp;
import adt.expressions.VarExp;
import adt.statements.*;
import adt.structures.*;
import adt.types.BoolType;
import adt.types.IntType;
import adt.types.StringType;
import adt.values.BoolValue;
import adt.values.IntValue;
import adt.values.StringValue;
import adt.values.Value;
import controller.Controller;
import repository.IRepository;
import repository.Repository;
import controller.MyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class View {
    IStmt example1 = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new NopStmt()));

    IStmt example2 = new CompStmt(new VarDeclStmt("a", new IntType()),
            new CompStmt(new VarDeclStmt("b", new IntType()),
                    new CompStmt(new AssignStmt("a", new ArithExp("+",
                            new ValueExp(new IntValue(2)), new ArithExp("*",
                            new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                            new CompStmt(new AssignStmt("b", new ArithExp("+",
                                    new VarExp("a"), new ValueExp(new
                                    IntValue(1)))), new PrintStmt(new VarExp("b"))))));

    IStmt example3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
            new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                            new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                                    new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                    new PrintStmt(new VarExp("v"))))));

    IStmt example4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
            new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                    new CompStmt(new OpenRFile(new VarExp("varf")),
                            new CompStmt(new VarDeclStmt("varc", new IntType()),
                                    new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                            new CompStmt(new PrintStmt(new VarExp("varc")),
                                                    new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                            new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                    new CloseRFile(new VarExp("varf"))))))))));

    public String readOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give option: ");
        return scanner.nextLine();
    }

    public void printOptions() {
        System.out.println("Which example would you like to run?");
        System.out.println("1.Example 1");
        System.out.println("2.Example 2");
        System.out.println("3.Example 3");
        System.out.println("4.Example 4");
        System.out.println("0.None,exit.");
    }

    public void run() {
        while (true) {
            int ok;
            this.printOptions();
            String option = this.readOption();
            if (option.equals("0")) {
                System.out.println("Leaving application...");
                break;
            }
            while (true) {
                System.out.println("For the chosen example,do you want to show the auxiliary structures?(Y/N)");
                String opt = this.readOption();
                if (opt.equals("Y")) {
                    ok = 1;
                    break;
                } else if (opt.equals("N")) {
                    ok = 0;
                    break;
                } else System.out.println("Incorrect input!");
            }
            switch (option) {
                case "1": {
                    System.out.println("1. " + example1);
                    if (ok == 1) {
                        this.runExample(example1, 1);
                    } else {
                        this.runExample(example1, 0);
                    }
                    break;
                }
                case "2": {
                    System.out.println("2. " + example2);
                    if (ok == 1) {
                        this.runExample(example2, 1);
                    } else {
                        this.runExample(example2, 0);
                    }
                    break;
                }
                case "3": {
                    System.out.println("3. " + example3);
                    if (ok == 1) {
                        this.runExample(example3, 1);
                    } else {
                        this.runExample(example3, 0);
                    }
                    break;
                }
                case "4": {
                    System.out.println("4. " + example4);
                    if (ok == 1) {
                        this.runExample(example4, 1);
                    } else {
                        this.runExample(example4, 0);
                    }
                    break;
                }
            }
        }
    }

    public void runExample(IStmt ex, int flag) {
        MyIStack<IStmt> stk = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTbl = new MyDictionary<String, Value>();
        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        MyIList<Value> out = new MyList<Value>();
        MyIHeap heap = new MyHeap();
        PrgState crtPrgState = new PrgState(stk, symTbl, out, ex,fileTable,heap);
        IRepository repo = new Repository(crtPrgState,"test.in");
        repo.add(crtPrgState);
        Controller controller = new Controller(repo);

        try {
            controller.allStep(flag);
        } catch (MyException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
