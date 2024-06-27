package com.example.calculator.ast;

/**
 * Represents a variable.
 */
public class Variable extends Atom {
    
    private String identifier;

    /**
     * Creates a new variable.
     * @param identifier  the identifier of the variable
     */
    public Variable(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets the variable's identifier.
     * @return  string representation of the identifier
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * Sets the variable's identifier.
     * @param identifier  identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Text representation of the variable.
     * @return  string representation of the variable
     */
    @Override
    public String toString() {
        return this.identifier;
    }

    /**
     * Gets the variable name.
     * @return  string representation of the variable
     */
    @Override
    public String getName() {
        return this.toString();
    }

    /**
     * Checks if an object represents variable.
     * @param other  object to check
     * @return  true if the given object is a variable, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Variable) {
            return this.equals((Variable) other);
        } else {
            return false;
        }
    }
    
    /**
     * Checks if two variables are equal.
     * @param other  second variable to check
     * @return  true if the variables are equal, false otherwise
     */
    public boolean equals(Variable other) {
        return this.identifier.equals(other.identifier);
    }

    /**
     * Evaluates a variable.
     * @param vars  stored variables
     * @return  symbolic expression depending on the reducability of the expression
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        if (vars.containsKey(this)) {
            return vars.get(this);
        }
        return new Variable(this.identifier);
    }

    /**
     * Computes a hashcode for the variable based on its identifier
     * @return  integer hashcode
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.identifier.hashCode();
        
        return result;
    }

}