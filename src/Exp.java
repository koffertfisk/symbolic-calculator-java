package com.example.calculator.ast;

/**
 * Represents an exponential operation.
 */
public class Exp extends Unary {

    /**
     * Creates a new exponential operation.
     * @param argument  expression to operate on
     */
    public Exp(SymbolicExpression argument) {
        super(argument);
    }

    /**
     * Gets exponential operator representation.
     * @return  text representation of exponential operator
     */
    @Override
    public String getName() {
        return "exp";
    }

    /**
     * Checks if an object represents an exponential operation.
     * @param other  object to check
     * @return  true if the given object is an exponential operation, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Exp) {
            return this.equals((Exp) other);
        } else {
            return false;
        }
    }

    /**
     * Checks if two exponential operations are equal.
     * @param other  second exponential operation to check
     * @return  true if the exponential operations are equal, false otherwise
     */
    public boolean equals(Exp other) {
        return super.equals(other);
    }

    /**
     * Evalues an exponential operation.
     * @param vars  stored variables
     * @return  symbolic expression depending on the reducability of the expression
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression arg = this.getArgument().eval(vars);
        if (arg.isConstant()) {
            return new Constant(Math.exp(arg.getValue()));
        }
        else {
            return new Exp(arg);
        }
    }

}
