package com.example.calculator.ast;

/**
 * Represents a matemathical expression or command.
 */
public abstract class SymbolicExpression {

    /**
     * Checks if an expression is constant.
     * @return  true in case of a constant expression, false otherwise.
     */
    public boolean isConstant() {
        return false;
    }

    /**
     * Gets the name of the expression.
     * @return string representing the name
     * @throws RuntimeException  since the super class has no name
     */
    public String getName() throws RuntimeException {
        throw new RuntimeException("getName() called on expression with no operator");
    }

    /**
     * Get the expression's value.
     * @return  double representation of the value
     * @throws RuntimeException  since the super class has no name
     */
    public double getValue() throws RuntimeException {
        throw new RuntimeException("getValue() called on expression with no operator");
    }

    /**
     * Gets expression priority for determining order of operation
     * @return  100 by default
     */
    public int getPriority() {
        return 100;
    }

    /**
     * Text representation of an expression.
     * @return  string representation of an expression
     * @throws RuntimeException  since the super class has no string representation
     */
    public String toString() throws RuntimeException {
        throw new RuntimeException("no string representation for base class");
    }
    
    /**
     * Evaluates an expression.
     * @param vars  stored variables
     * @return  a symbolic expression depending on the reducability of the expression
     */
    public abstract SymbolicExpression eval(Environment vars);
    
    /**
     * Checks whether an instance is a command.
     * @return  false by default
     */
    public boolean isCommand() {   
        return false;
    }

    /**
     * Checks if two expressions are equal.
     * @param other  other object to check
     * @return  true if the two are equal, false otherwise
     */
    public abstract boolean equals(Object other);
}