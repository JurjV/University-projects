package com.example.quantfx.model.statements;

import com.example.quantfx.exceptions.GenericException;
import com.example.quantfx.model.expressions.Expression;
import com.example.quantfx.model.expressions.VariableExpression;
import com.example.quantfx.model.types.BoolType;
import com.example.quantfx.model.types.Type;
import com.example.quantfx.model.values.BoolValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Dictionary;
import com.example.quantfx.program.Heap;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.program.Stack;

public class ConditionalAssignmentStatement implements Statement {
    String var;
    Expression condition;
    Expression thenExpression;
    Expression elseExpression;

    public ConditionalAssignmentStatement(String var, Expression condition, Expression thenExpression, Expression elseExpression) {
        this.var = var;
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        Stack<Statement> stack = state.getStack();
        stack.push(new IfStatement(
                condition,
                new AssignmentStatement(var, thenExpression),
                new AssignmentStatement(var, elseExpression)
        ));
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s=(%s)? %s: %s", var, condition, thenExpression, elseExpression);
    }

    @Override
    public Statement deepCopy() {
        return new ConditionalAssignmentStatement(var, condition.deepCopy(), thenExpression.deepCopy(), elseExpression.deepCopy());
    }

    @Override
    public Dictionary<String, Type> typeCheck(Dictionary<String, Type> typeEnv) throws GenericException {

        Type variableType = new VariableExpression(var).typeCheck(typeEnv);
        Type type1 = condition.typeCheck(typeEnv.deepCopy());
        Type type2 = thenExpression.typeCheck(typeEnv.deepCopy());
        Type type3 = elseExpression.typeCheck(typeEnv.deepCopy());
        if (type1.equals(new BoolType()) && type2.equals(variableType) && type3.equals(variableType))
            return typeEnv;
        else
            throw new GenericException("The conditional assignment is invalid!");
    }
}
