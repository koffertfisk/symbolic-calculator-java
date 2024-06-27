package com.example.calculator.ast;

/**
 * Represents negation.
 */
public class Negation extends Unary {
    
    /**
     * Creates a new negation.
     * @param argument  expression to negate
     */
    public Negation(SymbolicExpression argument) {
        super(argument);
    }

    /**
     * Gets negation operator representation.
     * @return  text representation of negation operator
     */
    @Override
    public String getName() {
        return "-";
    }

    /**
     * Negates a number.
     * @return  negated double number
     */
    @Override
    public double getValue()
    {
        return this.getValue() * -1.0;
    }

    /**
     * Checks if an object represents a negation operation.
     * @param other  object to check
     * @return  true if the given object is a negation operation, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Negation) {
            return this.equals((Negation) other);
        } else {
            return false;
        }
    }

    /**
     * Checks if two negation operations are equal.
     * @param other  second negation operation to check
     * @return  true if the negation operations are equal, false otherwise
     */
    public boolean equals(Negation other) {
        return super.equals(other);
    }

    /**
     * Text representation of a negation.
     * @return  string representation of a negation
     */
    @Override
    public String toString() {
        return this.getName() + this.getArgument().toString();
    }

    /**
     * Evalues a negation operation.
     * @param vars  stored variables
     * @return  symbolic expression depending on the reducability of the expression
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression arg = this.getArgument().eval(vars);
        if (arg.isConstant()) {
            return new Constant(arg.getValue() * -1.0);
        } else {
            return new Negation(arg);
        }
    }

}
