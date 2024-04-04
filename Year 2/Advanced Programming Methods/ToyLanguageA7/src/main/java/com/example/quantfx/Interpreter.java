package com.example.quantfx;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.*;
import com.example.quantfx.model.statements.*;
import com.example.quantfx.model.types.*;
import com.example.quantfx.model.values.*;
import com.example.quantfx.program.*;

public class Interpreter {
    public java.util.List<ExampleData> examples;

    public void initialize() {
        examples = new java.util.ArrayList<>();
        //examples.add(getExample16());
        //examples.add(getExample17());
        //examples.add(getExample15());
//        examples.add(getExample14());
        //examples.add(getExample13());
        //examples.add(getExample12());
        //examples.add(getExample1());
//        examples.add(getExample2());
        //examples.add(getExample3());
        //examples.add(getExample4());
        //examples.add(getExample5());
        //examples.add(getExample6());
        //examples.add(getExample7());
        //examples.add(getExample8());
//        examples.add(getExample9());
        //examples.add(getExample10());
        //examples.add(getExample11());
        examples.add(getExample20());
    }

    private ExampleData getExample14() {
        Statement example = new CompoundStatement(
                new DeclarationStatement("b", new BoolType()),
                new CompoundStatement(
                        new DeclarationStatement("c", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("b", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new ConditionalAssignmentStatement(
                                                "c",
                                                new VariableExpression("b"),
                                                new ValueExpression(new IntValue(100)),
                                                new ValueExpression(new IntValue(200))
                                        ),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("c")),
                                                new CompoundStatement(
                                                        new ConditionalAssignmentStatement(
                                                                "c",
                                                                new ValueExpression(new BoolValue(false)),
                                                                new ValueExpression(new IntValue(100)),
                                                                new ValueExpression(new IntValue(200))
                                                        ),
                                                        new PrintStatement(new VariableExpression("c"))
                                                )
                                        )
                                )
                        )
                )
        );

        return new ExampleData(example.toString(), example);
    }

    private ExampleData getExample12() {
        Statement example = new CompoundStatement(
                new DeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                        new ForStatement(
                                "v",
                                new ValueExpression(new IntValue(0)),
                                new ValueExpression(new IntValue(10)),
                                new ArithmeticExpression(
                                        new VariableExpression("v"),
                                        new ValueExpression(new IntValue(1)),
                                        ArithmeticOperator.ADD
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        return new ExampleData(example.toString(), example);
    }

    private ExampleData getExample13() {
        Statement example = new CompoundStatement(
                new CompoundStatement(
                        new DeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(
                                new HeapAllocationStatement("a", new ValueExpression(new IntValue(20))),
                                new ForStatement(
                                        "v",
                                        new ValueExpression(new IntValue(0)),
                                        new ValueExpression(new IntValue(3)),
                                        new ArithmeticExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)),
                                                ArithmeticOperator.ADD
                                        ),
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new AssignmentStatement(
                                                                "v",
                                                                new ArithmeticExpression(
                                                                        new VariableExpression("v"),
                                                                        new ReadHeapExpression(new VariableExpression("a")),
                                                                        ArithmeticOperator.MULTIPLY
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                ),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );

        return new ExampleData(example.toString(), example);
    }

    private ExampleData getExample1() {
        Statement example = new CompoundStatement(
                new DeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new CompoundStatement(
                                new PrintStatement(new LogicExpression(
                                        new ValueExpression(new BoolValue(true)),
                                        new ValueExpression(new BoolValue(false)),
                                        LogicalOperator.OR
                                )),
                                new DeclarationStatement("v", new IntType())
                        )
                )
        );

        String text = example.toString();

        return new ExampleData(text, example);
    }

    private ExampleData getExample2() {

        Statement statement = new CompoundStatement(
                new DeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new DeclarationStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ArithmeticExpression(
                                        new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)),
                                                ArithmeticOperator.MULTIPLY
                                        ),
                                        ArithmeticOperator.ADD
                                )),
                                new CompoundStatement(
                                        new AssignmentStatement("b", new ArithmeticExpression(
                                                new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)),
                                                ArithmeticOperator.ADD
                                        )),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        String text = statement.toString();

        return new ExampleData(text, statement);
    }

    private ExampleData getExample3() {
        String text = "Example 3\n" +
                "bool a\n" +
                "int v\n" +
                "a = true\n" +
                "if a then v = 2 else v = 3\n" +
                "Print(v)\n";

        Statement statement = new CompoundStatement(
                new DeclarationStatement("a", new BoolType()),
                new CompoundStatement(
                        new DeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );

        return new ExampleData(text, statement);
    }

    private ExampleData getExample4() {
        String text = "Example 4\n" +
                "string varf;\n" +
                "varf = \"test.in\";\n" +
                "openRFile(varf);\n" +
                "int varc;\n" +
                "readFile(varf, varc);\n" +
                "print(varc);\n" +
                "readFile(varf, varc);\n" +
                "print(varc);\n" +
                "closeRFile(varf)\n";

        Statement statement = null;

        try {
            statement = new CompoundStatement(
                    new DeclarationStatement("varf", new StringType()),
                    new CompoundStatement(
                            new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                            new CompoundStatement(
                                    new OpenReadFileStatement(new VariableExpression("varf")),
                                    new CompoundStatement(
                                            new DeclarationStatement("varc", new IntType()),
                                            new CompoundStatement(
                                                    new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                    new CompoundStatement(
                                                            new PrintStatement(new VariableExpression("varc")),
                                                            new CompoundStatement(
                                                                    new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new ValueExpression(new BoolValue(new IntValue(3).isGreaterThan(new IntValue(4))))),
                                                                            new CompoundStatement(
                                                                                    new CloseReadFileStatement(new VariableExpression("varf")),
                                                                                    new NopStatement()
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
            statement.typeCheck(new Dictionary<String, Type>());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ExampleData(text, statement);
    }

    private ExampleData getExample5() {
        String text = "Example 5\n" +
                "int v;\n" +
                "new(v, 20);" +
                "Ref Ref int a;\n" +
                "new(a, v);\n" +
                "print(v);\n" +
                "print(a);\n";

        Statement statement = null;

        try {
            statement = new CompoundStatement(
                    new DeclarationStatement("v", new RefType(new IntType())),
                    new CompoundStatement(
                            new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(
                                    new DeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                    new CompoundStatement(
                                            new HeapAllocationStatement("a", new VariableExpression("v")),
                                            new CompoundStatement(
                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                    new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)), ArithmeticOperator.ADD))
                                            )
                                    )
                            )
                    )
            );
            statement.typeCheck(new Dictionary<String, Type>());
        } catch (GenericException e) {
            System.out.println("Wrong type in example 5");
        }

        return new ExampleData(text, statement);
    }

    private ExampleData getExample6() {
        Statement statement = null;
        try {
            statement = new CompoundStatement(
                    new DeclarationStatement("v", new RefType(new IntType())),
                    new CompoundStatement(
                            new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(
                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                    new CompoundStatement(
                                            new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5)), ArithmeticOperator.ADD))
                                    )
                            )
                    )
            );
            statement.typeCheck(new Dictionary<String, Type>());
        } catch (GenericException e) {
            System.out.println("Wrong type in example 6");
        }

        return new ExampleData(statement.toString(), statement);
    }

    private ExampleData getExample7() {
        String text = "Example 7\n" +
                "int v;\n" +
                "new(v, 20);" +
                "print(rH(v));\n" +
                "wH(v, 30);\n" +
                "print(rH(v) + 5);\n";

        Statement statement = null;

        try {
            statement = new CompoundStatement(
                    new DeclarationStatement("v", new IntType()),
                    new CompoundStatement(
                            new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(
                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                    new CompoundStatement(
                                            new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(new ArithmeticExpression(
                                                    new ReadHeapExpression(new VariableExpression("v")),
                                                    new ValueExpression(new IntValue(5)),
                                                    ArithmeticOperator.ADD
                                            ))
                                    )
                            )
                    )
            );
            statement.typeCheck(new Dictionary<String, Type>());
        } catch (GenericException e) {
            System.out.println("Wrong type in example 7");

        }

        return new ExampleData(text, statement);
    }

    private ExampleData getExample8() {
        String text = "Example 8\n" +
                "int v;\n" +
                "v=4;" +
                "while (v>0) {\n" +
                "    print(v);\n" +
                "    v=v-1;\n" +
                "}\n" +
                "print(v);\n";

        Statement statement = null;
        try {
            statement = new CompoundStatement(
                    new DeclarationStatement("v", new IntType()),
                    new CompoundStatement(
                            new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                            new CompoundStatement(
                                    new WhileStatement(
                                            new RelationalExpression(
                                                    new VariableExpression("v"),
                                                    new ValueExpression(new IntValue(0)),
                                                    RelationOperator.GREATER_THAN
                                            ),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("v")),
                                                    new AssignmentStatement(
                                                            "v",
                                                            new ArithmeticExpression(
                                                                    new VariableExpression("v"),
                                                                    new ValueExpression(new IntValue(1)),
                                                                    ArithmeticOperator.SUBTRACT
                                                            )
                                                    )
                                            )
                                    ),
                                    new PrintStatement(new VariableExpression("v"))
                            )
                    )
            );
            statement.typeCheck(new Dictionary<String, Type>());
        } catch (GenericException e) {
            System.out.println("Wrong type in example 8");
        }

        return new ExampleData(text, statement);
    }

    private ExampleData getExample9() {
        String text = "Example 9\n" +
                "int v;\n" +
                "v=4;" +
                "fork(print(v));\n" +
                "print(v);\n";

        Statement statement = null;
        try {
            statement = new CompoundStatement(
                    new DeclarationStatement("v", new IntType()),
                    new CompoundStatement(
                            new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                            new CompoundStatement(
                                    new ForkStatement(
                                            new PrintStatement(new VariableExpression("v"))
                                    ),
                                    new PrintStatement(new VariableExpression("v"))
                            )
                    )
            );
            statement.typeCheck(new Dictionary<String, Type>());
        } catch (GenericException e) {
            System.out.println("Wrong type in example 9");
        }

        return new ExampleData(text, statement);
    }

    private ExampleData getExample10() {
        String text = "Example 10\n" +
                "int v;\n" +
                "v=0;" +
                "repeat( \n" +
                "    fork(print(v));\n" +
                "    v=v-1;\n" +
                ") until v==3;\n" +
                "x=1;";

        Statement statement = null;

        try {
            statement = new CompoundStatement(
                    new DeclarationStatement("a", new RefType(new IntType())),
                    new CompoundStatement(
                            new DeclarationStatement("counter", new IntType()),
                            new WhileStatement(new RelationalExpression(new VariableExpression("counter"), new ValueExpression(new IntValue(10)), RelationOperator.LESS_THAN), new CompoundStatement(
                                    new ForkStatement(new ForkStatement(new HeapAllocationStatement("a", new VariableExpression("counter")))),
                                    new CompoundStatement(
                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))),
                                            new AssignmentStatement("counter", new ArithmeticExpression(new VariableExpression("counter"), new ValueExpression(new IntValue(1)), ArithmeticOperator.ADD))
                                    )
                            )
                            )
                    )
            );
            statement.typeCheck(new Dictionary<String, Type>());
        } catch (GenericException e) {
            System.out.println("Wrong type in example 10");
        }

        return new ExampleData(text, statement);
    }

    private ExampleData getExample11() {
        String text = "int v; Ref int a; v=10;new(a,22);\n" +
                "fork(wH(a,30);v=32;print(v);print(rH(a)));\n" +
                "print(v);print(rH(a))";

        Statement statement = null;

        try {
            statement = new CompoundStatement(
                    new DeclarationStatement("v", new IntType()),
                    new CompoundStatement(
                            new DeclarationStatement("a", new RefType(new IntType())),
                            new CompoundStatement(
                                    new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                    new CompoundStatement(
                                            new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                            new CompoundStatement(
                                                    new ForkStatement(
                                                            new CompoundStatement(
                                                                    new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                                    new CompoundStatement(
                                                                            new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                                            new CompoundStatement(
                                                                                    new PrintStatement(new VariableExpression("v")),
                                                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                            )
                                                                    )
                                                            )
                                                    ),
                                                    new CompoundStatement(
                                                            new PrintStatement(new VariableExpression("v")),
                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                    )
                                            )
                                    )
                            )
                    )
            );
            statement.typeCheck(new Dictionary<String, Type>());
        } catch (GenericException e) {
            System.out.println("Wrong type in example 11");
        }

        return new ExampleData(text, statement);
    }

    private ExampleData getExample20() {
        Statement statement=null;
        try {
            statement =
                    new CompoundStatement(
                            new DeclarationStatement("a", new RefType(new IntType())),
                            new CompoundStatement(
                                    new DeclarationStatement("b", new RefType(new IntType())),
                                    new CompoundStatement(
                                            new DeclarationStatement("v", new IntType()),
                                            new CompoundStatement(
                                                    new HeapAllocationStatement("a", new ValueExpression(new IntValue(0))),
                                                    new CompoundStatement(
                                                            new HeapAllocationStatement("b", new ValueExpression(new IntValue(0))),
                                                            new CompoundStatement(
                                                                    new HeapWritingStatement("a", new ValueExpression(new IntValue(1))),
                                                                    new CompoundStatement(
                                                                            new HeapWritingStatement("b", new ValueExpression(new IntValue(2))),
                                                                            new CompoundStatement(
                                                                                    new ConditionalAssignmentStatement(
                                                                                            "v",
                                                                                            new RelationalExpression(
                                                                                                    new ReadHeapExpression(new VariableExpression("a")),
                                                                                                    new ReadHeapExpression(new VariableExpression("b")),
                                                                                                    RelationOperator.LESS_THAN
                                                                                            ),
                                                                                            new ValueExpression(new IntValue(100)),
                                                                                            new ValueExpression(new IntValue(200))
                                                                                    ),
                                                                                    new CompoundStatement(
                                                                                            new PrintStatement(new VariableExpression("v")),
                                                                                            new CompoundStatement(
                                                                                                    new ConditionalAssignmentStatement(
                                                                                                            "v",
                                                                                                            new RelationalExpression(
                                                                                                                    new ArithmeticExpression(
                                                                                                                            new ReadHeapExpression(new VariableExpression("b")),
                                                                                                                            new ValueExpression(new IntValue(2)),
                                                                                                                            ArithmeticOperator.SUBTRACT
                                                                                                                    ),
                                                                                                                    new ReadHeapExpression(new VariableExpression("a")),
                                                                                                                    RelationOperator.GREATER_THAN
                                                                                                            ),
                                                                                                            new ValueExpression(new IntValue(100)),
                                                                                                            new ValueExpression(new IntValue(200))
                                                                                                    ),
                                                                                                    new PrintStatement(new VariableExpression("v"))
                                                                                            )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    );
            statement.typeCheck(new Dictionary<String, Type>());

        } catch (GenericException e) {
            System.out.println("Wrong type in example 20");
        }

        return new ExampleData(statement.toString(), statement);
    }

    class ExampleData {
        public String text;
        public Statement statement;

        public ExampleData(String text, Statement statement) {
            this.text = text;
            this.statement = statement;
        }
    }
}
