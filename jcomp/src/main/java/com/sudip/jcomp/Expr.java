package com.sudip.jcomp;

abstract class Expr {

    //Nested classes are here...
    static class Assign extends Expr {
        Assign(Token name, Expr value){
            this.name = name;
            this.value = value;
        }
        final Token name;
        final Expr value;
    }

    static class Binary extends Expr {

        Binary (Expr left, Token operator, Expr right) {
            this.left = left;
            this.right = right;
            this.operator = operator;
        }
        final Expr left;
        final Expr right;
        final Token operator;

    }

}
