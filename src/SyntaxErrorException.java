package com.example.calculator.parser;

/**
 * Exception thrown whenever the parser discover a syntax error.
 */
public class SyntaxErrorException extends Exception {
    /**
     * Creates a new syntax error exception without a message.
     */
    public SyntaxErrorException() {
        super();
    }
    /**
     * Creates a new syntax error exception with a message.
     * @param msg  exception message
     */
    public SyntaxErrorException(String msg) {
        super(msg);
    }
}