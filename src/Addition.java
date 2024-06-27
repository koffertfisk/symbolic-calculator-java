package com.example.calculator.ast;

/** 
 * Represents addition using the '+' symbol.
 */
public class Addition extends Binary {

    /**
     * Creates a new addition.
     * @param lhs  left hand side of the addition
     * @param rhs  right hand side of the addition
     */
    public Addition(SymbolicExpression lhs, SymbolicExpression rhs) {
        super(lhs, rhs);
    }

    /**
     * Gets addition operator representation.
     * @return  text representation of addition operator
     */
    @Override
    public String getName() {
        return "+";
    }

    /**
     * Checks if an object represents addition.
     * @param other  object to check
     * @return  true if the given object is an addition, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Addition) {
            return this.equals((Addition) other);
        } else {
            return false;
        }
    }

    /**
     * Checks if two additions are equal. 
     * @param other  second addition to check
     * @return  true if the additions are equal, false otherwise
     */
    public boolean equals(Addition other) {
        if (!super.operatorEquals(other)) {
            return false;
        }
        if (this.getlhs().equals(other.lhs) && (this.getrhs().equals(other.rhs))) {
            return true;
        }
        if (this.getlhs().equals(other.lhs) && (this.getrhs().equals(other.lhs))) {
            return true;
        }
        return false;
    }

    /**
     * Gets addition priority for determining order of operation
     * @return  fixed priority of 50
     */
    @Override
    public int getPriority() {
        return 50;
    }

    /**
     * Evaluates an addition.
     * @param lhs  left hand side of the addition
     * @param rhs  right hand side of the addition
     * @return  a number (constant) if both sides are numbers, otherwise 
     *          a new addition 
     */
    private SymbolicExpression eval(SymbolicExpression lhs, SymbolicExpression rhs) {
        if (lhs.isConstant() && rhs.isConstant()) {
            return new Constant(lhs.getValue() + rhs.getValue());
        } else {
            return new Addition(lhs, rhs);
        }
    }

    /**
     * Evaluates an addition.
     * @param vars  stored variables
     * @return  a symbolic expression depending on the reducability of the expression
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        if (this.getlhs().isConstant() && this.getrhs().isConstant()) {
            return this.eval(lhs, rhs);
        } else if (this.getlhs().isConstant() && !this.getrhs().isConstant()) {
            return this.eval(this.getlhs(), this.getrhs().eval(vars));
        } else if (!this.getlhs().isConstant() && this.getrhs().isConstant()) {
            return this.eval(this.getlhs().eval(vars), this.getrhs());
        }
        return this.eval(this.getlhs().eval(vars), this.getrhs().eval(vars));
    }

}
