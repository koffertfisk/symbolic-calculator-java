package com.example.calculator.ast;

/**
 * Represents a unary operation.
 */
public abstract class Unary extends SymbolicExpression
{
    private SymbolicExpression argument;

    /**
     * Creates a new unary operation.
     * @param argument  the expression to operate on
     */
    public Unary(SymbolicExpression argument) {
        this.argument = argument;
    }

    /**
     * Gets the unary expression.
     * @return  the unary expression
     */
    public SymbolicExpression getArgument() {
        return this.argument;
    }

    /**
     * Checks if this unary expression is equal to another.
     *
     * @param other the other unary expression to compare to
     * @return true if they are equal, false otherwise
     */
    public boolean equals(Unary other) {
        return (this.getName().equals(other.getName()) && this.getArgument().equals(other.getArgument()));
    }

    /**
     * Text representation of a unary operation.
     * @return  string representation of a unary operation
     */
    @Override
    public String toString() {
        return this.getName() + "(" + this.argument + ")";
    }
}