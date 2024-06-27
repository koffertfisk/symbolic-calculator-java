package com.example.calculator.ast;

/**
 * Represents division using the '/' symbol.
 */
public class Division extends Binary {
    
    /**
     * Creates a division
     * @param lhs  left hand side, i.e. the division nominator
     * @param rhs  right hand side, i.e. the division denominator
     */
    public Division(SymbolicExpression lhs, SymbolicExpression rhs) {
        super(lhs, rhs);
    }

    /**
     * Gets divison operator representation.
     * @return  text representation of divison operator
     */
    @Override
    public String getName() {
        return "/";
    }

    /**
     * Checks if an object represents division.
     * @param other  object to check
     * @return  true if the given object is an division, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Division) {
            return this.equals((Division) other);
        } else {
            return false;
        }
    }

    /**
     * Checks if two divisions are equal. 
     * @param other  second division to check
     * @return  true if the divisions are equal, false otherwise
     */
    public boolean equals(Division other) {
        if (!super.operatorEquals(other)) {
            return false;
        }
        if (this.getlhs().equals(other.lhs) && (this.getrhs().equals(other.rhs))) {
            return true;
        }
        return false;
    }

    /**
     * Evaluates a divison.
     * @param lhs  left hand side, i.e. the division nominator
     * @param rhs  right hand side, i.e. the division denominator
     * @return  a number (constant) if both sides are numbers, otherwise 
     *          a new division 
     */
    private SymbolicExpression eval(SymbolicExpression lhs, SymbolicExpression rhs) {
        if (lhs.isConstant() && rhs.isConstant()) {
            return new Constant(lhs.getValue() / rhs.getValue());
        } else {
            return new Division(lhs, rhs);
        }
    }

    /**
     * Evaluates a division.
     * @param vars  stored variables
     * @return  a symbolic expression depending on the reducability of the expression
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        if (this.getlhs().isConstant() && this.getrhs().isConstant()) {
            return this.eval(this.getlhs(), this.getrhs());
        } else if (!this.getlhs().isConstant() && this.getrhs().isConstant()) {
            return this.eval(this.getlhs().eval(vars), this.getrhs());
        } else if (this.getlhs().isConstant() && !this.getrhs().isConstant()) {
            return this.eval(this.getlhs(), this.getrhs().eval(vars));
        }
        return this.eval(this.getlhs().eval(vars), this.getrhs().eval(vars));
    }

}
