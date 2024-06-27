package com.example.calculator.ast;

/**
 * Represents an atomic operation.
 */
public abstract class Atom extends SymbolicExpression {
    
    /**
     * Creates a new atmoic operation
     */
    public Atom() {}

    /**
     * Gets text reprentation.
     * @return string reprentation of an atmoic operation
     */
    @Override
    public String toString() {
        return this.getName();
    }
}