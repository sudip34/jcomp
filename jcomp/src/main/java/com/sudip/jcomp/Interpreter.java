package com.sudip.jcomp;

import java.util.List;

public class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void>{
    private Environment environment = new Environment();

//    void interpret(Expr expression){
//        try{
//            Object value = evaluate(expression);
//            System.out.println(stringify(value));
//        }catch (RuntimeError error) {
//            Jcomp.runtimeError(error);
//        }
//    }
    void interpret(List<Stmt> statements){
        try {
            for (Stmt statement: statements) {
                execute(statement);
            }
        }catch (RuntimeError error){
            Jcomp.runtimeError(error);
        }
    }

    private void execute(Stmt stmt){
        stmt.accept(this);
    }

    @Override
    public Object visitAssaignExpr(Expr.Assign expr) {
        return null;
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);
        switch(expr.operator.type) {
            case BANG_EQUAL -> { return !isEqual(left,right); }
            case EQUAL_EQUAL -> { return !isEqual(left,right); }
            case GRATER -> {
                checkNumberOperands(expr.operator, left, right);
                return (double)left > (double) right;
            }
            case GRATER_EQUAL -> {
                checkNumberOperands(expr.operator, left, right);
                return (double)left >= (double) right;
            }
            case LESS -> {
                checkNumberOperands(expr.operator, left, right);
                return (double)left < (double) right;
            }
            case LESS_EQUAL -> {
                checkNumberOperands(expr.operator, left, right);
                return (double)left <= (double)right;
            }
            case MINUS -> { return (double)left- (double)right; }
            case PLUS -> {
                if (left instanceof Double && right instanceof Double) {
                    return (double)left + (double)right;
                }

                if (left instanceof String && right instanceof String) {
                    return (String)left + (String)right;
                }
                throw new RuntimeError(expr.operator, "Operands must be two numbers or two strings");
            }
            case SLASH -> { return (double)left/(double)right; }
            case STAR -> { return (double)left * (double)right; }
        }

        return null;
    }

    @Override
    public Object visitCallExpr(Expr.Call expr) {
        return null;
    }

    @Override
    public Object visitGetExpr(Expr.Get expr) {
        return null;
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return null;
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return null;
    }

    @Override
    public Object visitLogicalExpr(Expr.Logical expr) {
        return null;
    }

    @Override
    public Object visitSetExpr(Expr.Set expr) {
        return null;
    }

    @Override
    public Object visitSuperExpr(Expr.Super expr) {
        return null;
    }

    @Override
    public Object visitThisExpr(Expr.This expr) {
        return null;
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);
        switch(expr.operator.type){
            case BANG -> {return !isTruthy(right); }
            case MINUS -> checkNumberOperand(expr.operator,right);
            default -> { return -(double) right; }
        }
        return null;
    }

    private boolean isTruthy(Object object) {
        if (object == null) return  false;
        if (object instanceof Boolean) return (boolean)object;
        return true;
    }

    @Override
    public Object visitVariableExpr(Expr.Variable expr) {
        return environment.get(expr.name);
    }


    private String stringify(Object object) {
        if (object == null ) return "nil";

        if(object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length()-2);
            }

            return text;
        }
        return object.toString();
    }


//<Statements adn State interpret
//> evaluate
    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }
//> evaluate

// checkNumberOperands
    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) return ;
        throw new RuntimeError(operator, "Operands must be number");
    }

// checkNumberOperands
    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return ;
        throw new RuntimeError(operator, "Operands must be number");
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null ) return true;
        if (a == null) return false;
        return a.equals(b);
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        return null;
    }

    @Override
    public Void visitClassStmt(Stmt.Class stmt) {
        return null;
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        evaluate(stmt.expression);
        return null;
    }

    @Override
    public Void visitFunctionStmt(Stmt.Function stmt) {
        return null;
    }

    @Override
    public Void visitIfStmt(Stmt.If stmt) {
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
        Object value = evaluate(stmt.expression);
        System.out.println(stringify(value));
        return null;
    }

    @Override
    public Void visitReturnStmt(Stmt.Return stmt) {
        return null;
    }

    @Override
    public Void visitVarStmt(Stmt.Var stmt) {
        Object value = null;
        if (stmt.initializer != null) {
            value = evaluate(stmt.initializer);
        }

        environment.define(stmt.name.lexeme, value);
        return null;
    }

    @Override
    public Void visitWhileStmt(Stmt.While stmt) {
        return null;
    }
}
