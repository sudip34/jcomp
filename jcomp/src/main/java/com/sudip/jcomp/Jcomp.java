package com.sudip.jcomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Jcomp {
    static boolean hadError = false;
    public static void main(String[] args) throws Exception {
        if(args.length>1){
            System.out.println("Usage: Jcomp [script]");
            if (hadError)System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }

    }

    private static void runFile(String path) throws Exception{
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    private static void runPrompt() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (;;){
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false; // if the user makes a mistake it shouldn't kill their entire session
        }
    }


    private static void run(String source) {
//        Scanner scanner = new Scanner(source);
//        List<Token> tokens = scanner.sca
    }


    static void error(int line, String message){
        report(line,"", message);
    }

    private static void report(int line, String where, String message) {
        System.err.print("[line " + line +" ] Error" + where + ": "+message);
        hadError = true;
    }

}