package com.example.calculator.ast;

/**
 * Represents a number.
 */
public class Constant extends Atom {
    
    private double value;

    /**
     * Creates a new number.
     * @param value  the value to assign to a constant
     */
    public Constant(double value)
    {
        this.value = value;
    }

    /**
     * Checks whether an instance is a constant.
     * @return  true in case of a constant, false otherwise
     */
    @Override
    public boolean isConstant() {
        return true;
    }

    /**
     * Gets a constants numerical value.
     * @return  double representation of the value
     */
    @Override
    public double getValue() {
        return this.value;
    }

    /**
     * Text representation of a constant, i.e. its value.
     * @return  string representation of a constant's value
     */
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    /**
     * Gets a constant's name, i.e. its value.
     * @return  string representation of a constant.
     */
    @Override
    public String getName() {
        return this.toString();
    }

    /** 
     * Check if an object represents a constant.
     * @return  true if the given object is a constant, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Constant) {
            return this.equals((Constant) other);
        } else {
            return false;
        }
    }
    
    /**
     * Checks if two constants are equal by comparing their values.
     * @param other  second constant to check
     * @return  true if the constants are equal, false otherwise
     */
    public boolean equals(Constant other) {
        return this.value == other.value;
    }

    /**
     * Evaluates a constant, i.e. reduces itself
     * @return  itself since it is already fully reduced
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        return this;
    }

}