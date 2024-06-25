package com.sudip.jcomp;//> Appendix II expri

import java.util.List;

sealed interface ExprI permits  ExprI.Assign, ExprI.Binary, ExprI.Call, ExprI.Get, ExprI.Grouping, ExprI.Literal, ExprI.Logical, ExprI.Set, ExprI.Super, ExprI.This, ExprI.Unary, ExprI.Variable {
    interface Visitor<R> {
    R visitAssignExprI(Assign expri);
    R visitBinaryExprI(Binary expri);
    R visitCallExprI(Call expri);
    R visitGetExprI(Get expri);
    R visitGroupingExprI(Grouping expri);
    R visitLiteralExprI(Literal expri);
    R visitLogicalExprI(Logical expri);
    R visitSetExprI(Set expri);
    R visitSuperExprI(Super expri);
    R visitThisExprI(This expri);
    R visitUnaryExprI(Unary expri);
    R visitVariableExprI(Variable expri);
   }
//> expri-assign
     record Assign(Token name, ExprI value) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitAssignExprI(this);
      }
}
//> expri-binary
     record Binary(ExprI left, Token operator, ExprI right) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitBinaryExprI(this);
      }
}
//> expri-call
     record Call(ExprI callee, Token paren, List<ExprI> arguments) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitCallExprI(this);
      }
}
//> expri-get
     record Get(ExprI object, Token name) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitGetExprI(this);
      }
}
//> expri-grouping
     record Grouping(ExprI expression) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitGroupingExprI(this);
      }
}
//> expri-literal
     record Literal(Object value) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitLiteralExprI(this);
      }
}
//> expri-logical
     record Logical(ExprI left, Token operator, ExprI right) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitLogicalExprI(this);
      }
}
//> expri-set
     record Set(ExprI object, Token name, ExprI value) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitSetExprI(this);
      }
}
//> expri-super
     record Super(Token keyword, Token method) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitSuperExprI(this);
      }
}
//> expri-this
     record This(Token keyword) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitThisExprI(this);
      }
}
//> expri-unary
     record Unary(Token operator, ExprI right) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitUnaryExprI(this);
      }
}
//> expri-variable
     record Variable(Token name) implements ExprI {
      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitVariableExprI(this);
      }
}

    abstract <R> R accept(Visitor<R> visitor);
}
