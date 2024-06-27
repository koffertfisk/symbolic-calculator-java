package com.example.calculator.ast;

/**
 * Represents a sin operation.
 */
public class Sin extends Unary {

    /**
     * Creates a new sin operation
     * @param argument  the expression to operate on
     */
    public Sin(SymbolicExpression argument) {
        super(argument);
    }

    /**
     * Gets sin operator representation.
     * @return  text representation of sin operator
     */
    @Override
    public String getName() {
        return "sin";
    }

    /**
     * Checks if an object represents sin.
     * @param other  object to check
     * @return  true if the given object is a sin operation, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Sin) {
            return this.equals((Sin) other);
        } else {
            return false;
        }
    }
    
    /**
     * Checks if two sin operations are equal.
     * @param other  second sin operation to check
     * @return  true if the sin operations are equal, false otherwise
     */
    public boolean equals(Sin other) {
        return super.equals(other);
    }

    /**
     * Evaluates a sin operation.
     * @param vars  stored variables
     * @return  symbolic expression depending on the reducability of the expression
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression arg = this.getArgument().eval(vars);
        if (arg.isConstant()) {
            return new Constant(Math.sin(arg.getValue()));
        } else {
            return new Sin(arg);
        }
    }

}
