package com.example.calculator.ast;

/**
 * Represents known named constants such as 'pi', 'e' etc.
 */
public class NamedConstant extends Constant {
    
    private String name;

    /**
     * Creates a new named constant.
     * @param name  the constant's name
     * @param value  the constant's value
     */
    public NamedConstant(String name, double value) {
        super(value);
        this.name = name;
    }

    /** 
     * Check if an object represents a named constant.
     * @return  true if the given object is a named constant, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof NamedConstant) {
            return this.equals((NamedConstant) other);
        } else {
            return false;
        }
    }
    
    /**
     * Checks if two named constants are equal by comparing their values and names.
     * @param other  second named constant to check
     * @return  true if the named constants are equal, false otherwise
     */
    public boolean equals(NamedConstant other) {
        return (this.getValue() == other.getValue() && this.name.equals(other.name));
    }

    /**
     * Gets a named constant's name.
     * @return  the named constant's name
     */
    @Override
    public String getName(){
        return this.name;
    }

    /**
     * Text representation of a named constant, i.e. its name.
     * @return  string representing the named constant's name
     */
    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * Evaluates a named constant, i.e. reduces itself to a constant.
     * @return  a constant with the named constant's value
     */
    @Override 
    public SymbolicExpression eval(Environment vars) {
        return new Constant(this.getValue());
    }

}