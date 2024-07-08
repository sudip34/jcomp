package com.sudip.jcomp;

public class Interpreter implements Expr.Visitor<Object>{

    void interpret(Expr expression){
        try{
            Object value = evaluate(expression);
            System.out.println(stringify(value));
        }catch (RuntimeError error) {
            Jcomp.runtimeError(error);
        }
    }

    @Override
    public Object visitAssignExpr(Expr.Assign expr) {
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
        return null;
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

}
