package com.example.calculator.ast;

/**
 * Represents a common logarithm (base 10) operation.
 */
public class Log extends Unary {
    
    /**
     * Creates a new logarithm operation.
     * @param argument  expression to operate on
     */
    public Log(SymbolicExpression argument) {
        super(argument);
    }

    /**
     * Gets logarithm operator representation.
     * @return  text representation of logarithm operator
     */
    @Override
    public String getName() {
        return "log";
    }

    /**
     * Checks if an object represents a logarithm operation.
     * @param other  object to check
     * @return  true if the given object is a logarithm operation, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Log) {
            return this.equals((Log) other);
        } else {
            return false;
        }
    }

    /**
     * Checks if two logarithm operations are equal.
     * @param other  second logarithm operation to check
     * @return  true if the logarithm operations are equal, false otherwise
     */
    public boolean equals(Log other) {
        return super.equals(other);
    }

    /**
     * Evalues a logarithm operation.
     * @param vars  stored variables
     * @return  symbolic expression depending on the reducability of the expression
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression arg = this.getArgument().eval(vars);
        if (arg.isConstant()) {
            return new Constant(Math.log10(arg.getValue()));
        }
        else {
            return new Log(arg);
        }
    }

}
