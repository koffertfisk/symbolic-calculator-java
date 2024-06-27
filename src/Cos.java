package com.example.calculator.ast;

/**
 * Represents a cos operation.
 */
public class Cos extends Unary {
    
    /**
     * Creates a new cos operation
     * @param argument  the expression to operate on
     */
    public Cos(SymbolicExpression argument) {
        super(argument);
    }

    /**
     * Gets cos operator representation.
     * @return  text representation of cos operator
     */
    @Override
    public String getName() {
        return "cos";
    }
    
    /**
     * Checks if an object represents cos.
     * @param other  object to check
     * @return  true if the given object is an cos operation, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Cos) {
            return this.equals((Cos) other);
        } else {
            return false;
        }
    }
    
    /**
     * Checks if two cos operations are equal.
     * @param other  second cos operation to check
     * @return  true if the cos operations are equal, false otherwise
     */
    public boolean equals(Cos other) {
        return super.equals(other);
    }

    /**
     * Evalues a cos operation.
     * @param vars  stored variables
     * @return  symbolic expression depending on the reducability of the expression
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression arg = this.getArgument().eval(vars);
        if (arg.isConstant()) {
            return new Constant(Math.cos(arg.getValue()));
        } else {
            return new Cos(arg);
        }
    }

}
