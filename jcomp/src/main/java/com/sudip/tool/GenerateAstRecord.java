package com.sudip.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAstRecord {
    public static void main(String[] args) throws IOException {
        if (args.length !=1){
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args [0];
        //> call-define-ast
        defineAst(outputDir, "ExprI", List.of(
                //statements and state assign-expr
                "Assign   : Token name, ExprI value",
                "Binary   : ExprI left, Token operator, ExprI right",
                "Call     : ExprI callee, Token paren, List<ExprI> arguments",
                "Get      : ExprI object, Token name",
                "Grouping : ExprI expression",
                "Literal  : Object value",
                "Logical  : ExprI left, Token operator, ExprI right",
                "Set      : ExprI object, Token name, ExprI value",
                "Super    : Token keyword, Token method",

                "This     : Token keyword",

                "Unary    : Token operator, ExprI right",
                "Variable : Token name"
        ));
        defineAst(outputDir, "Stmt", Arrays.asList(
                "Expression : Expr expression",
                "Print      : Expr expression"
        ));
    }


    //> define-ast
    private static void defineAst(
            String outputDir, String baseName, List<String> types
    ) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        String permitString = "";
        for (String type : types) {
            String recordName = type.split(":")[0].trim();
            permitString = permitString+" "+baseName+"."+recordName+",";
        }
        permitString = permitString.substring(0,permitString.length()-1);

        try(PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println("//> Appendix II " + baseName.toLowerCase());
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println("sealed interface " + baseName + " permits "+ permitString +" {");
    //define visitor class here
            defineVisitor(writer, baseName, types);
    // the AST classes are define inside the base class
            for (String type : types) {
                String recordName = type.split(":")[0].trim();
                String fields = type.split(":")[1].trim();
                defineType(writer, baseName, recordName, fields);
            }
// the base accept() method.
            writer.println();
            writer.println("    abstract <R> R accept(Visitor<R> visitor);");
            writer.println("}");
            writer.close();
        }
    }

    private static void defineType(PrintWriter writer, String baseName, String recordName, String fieldList) {
        writer.println("//> " + baseName.toLowerCase() + "-" + recordName.toLowerCase());

//> omit
// Hack. Stmt.Class has such a long constructor that it overflows
// the line length on the Appendix II page. Wrap it.
        if (fieldList.length()>64) {
            fieldList = fieldList.replace(", ", ",\n               ");
        }
//constructor
        writer.println("     record " + recordName + "(" + fieldList + ") implements " +baseName +" {");
        writer.println("      @Override");
        writer.println("      public <R> R accept(Visitor<R> visitor) {");
        writer.println("          return visitor.visit" + recordName + baseName + "(this);");
        writer.println("      }");
        writer.println("}");


    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("    interface Visitor<R> {");
        for (String type: types){
            String typeName = type.split(":")[0].trim();
            writer.println("    R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");

        }
        writer.println("   }");
    }
}

