package com.sudip.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length !=1){
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args [0];
        //> call-define-ast
        defineAst(outputDir, "Expr", Arrays.asList());
    }


    //> define-ast
    private static void defineAst(
            String outputDir, String baseName, List<String> types
    ) throws IOException {
        String path = outputDir + "/" + baseName + ".java";

       try(PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
           writer.println("//> Appendix II" + baseName.toLowerCase());
           writer.println();
           writer.println("import java.util.List;");
           writer.println();
           writer.println("abstract class " + baseName + " {");
// the AST classes are define inside the base class
           for (String type : types) {
               String className = type.split(":")[0].trim();
               String fields = type.split(":")[1].trim();
               defineType(writer, baseName, className, fields);
           }
           writer.println("}");
           writer.close();
       }
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        writer.println("//> " + baseName.toLowerCase() + "-" + className.toLowerCase());
        writer.println(" static class " + className +" extends "+ baseName + " {");

//> omit
// Hack. Stmt.Class has such a long constructor that it overflows
// the line length on the Appendix II page. Wrap it.
        if (fieldList.length()>64) {
            fieldList = fieldList.replace(", ", ",\n               ");
        }
//constructor
        writer.println("     " + className + "(" + fieldList + ") {");
        fieldList = fieldList.replace(",\n         ", ",  ");
        //store parameters in fields
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.println("     this." + name +" = " + name +";");
        }
        writer.println("    }");

        // Fields
        writer.println();
        for (String field : fields) {
            writer.println("    final " + field + ";");
        }
        writer.println("    }");
        writer.println("    }");
    }
}
