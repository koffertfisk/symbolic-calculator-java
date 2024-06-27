package com.example.calculator.parser;

/**
 * Exception thrown whenever an expression is not supported or malformed.
 */
public class IllegalExpressionException extends RuntimeException {
    /**
     * Creates a new illegal expression exception without a message.
     */
    public IllegalExpressionException() {
        super();
    }
    /**
     * Creates a new illegal expression exception with a message.
     * @param msg  exception message
     */
    public IllegalExpressionException(String msg) {
        super(msg);
    }
}