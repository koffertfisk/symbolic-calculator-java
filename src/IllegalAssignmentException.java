package com.example.calculator.ast;

/**
 * Exception thrown upon trying to redefine a "protected" variable.
 */
public class IllegalAssignmentException extends RuntimeException {
    /**
     * Creates a new illegal assignment exception without a message.
     */
    public IllegalAssignmentException () {
        super();
    }
    /**
     * Creates a new illegal assignment exception with a message.
     * @param msg  exception message
     */
    public IllegalAssignmentException(String msg) {
        super(msg);
    }
}