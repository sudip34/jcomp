package com.sudip.jcomp;

import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    Scanner(String source){
        this.source = source;
    }
    List<Token> scanTokens(){
        while (!isAtEnd()){
            // we are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current>= source.length();
    }

    private void scanToken() {
        char c = advance();
        switch (c){
            case '(' -> addToken(TokenType.LEFT_PAREN);
            case ')' -> addToken(TokenType.RIGHT_PAREN);
            case '{' -> addToken(TokenType.LEFT_BRACE);
            case '}' -> addToken(TokenType.RIGHT_BRACE);
            case ',' -> addToken(TokenType.COMMA);
            case '.' -> addToken(TokenType.DOT);
            case '-' -> addToken(TokenType.MINUS);
            case '+' -> addToken(TokenType.PLUS);
            case ';' -> addToken(TokenType.SEMICOLON);
            case '*' -> addToken(TokenType.STAR);
            case '!' -> addToken(match('=')? TokenType.BANG_EQUAL : TokenType.BANG);
            case '=' -> addToken(match('=')? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
            case '<' -> addToken(match('=')? TokenType.LESS_EQUAL : TokenType.LESS);
            case '>' -> addToken(match('=')? TokenType.GRATER_EQUAL : TokenType.GRATER);
            case '/' -> {
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.SLASH);
                }
            }
            case ' ', '\r','\t' -> {}              //ignore the white space
            case '\n' -> line++;
            default -> Jcomp.error(line, "Unexpected character");
        }
    }

    private char peek() {
        if(isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char advance(){
        current++;
        return source.charAt(current-1);
    }

    private void addToken(TokenType type){
        addToken(type, null);

    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
    private boolean match(char expected) {
        if(isAtEnd()) return false;
        if (source.charAt(current) != expected) return  false;

        current++;
        return true;
    }


}
