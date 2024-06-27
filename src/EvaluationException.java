package com.example.calculator.ast;

/**
 * Exception thrown whether evaluation fails for some reason.
 */
public class EvaluationException extends RuntimeException {
    /**
     * Creates a new evaluation exception without a message.
     */
    public EvaluationException() {
        super();
    }
    /**
     * Creates a new evaluation exception with a message.
     * @param msg  excpetion message
     */
    public EvaluationException(String msg) {
        super(msg);
    }
}