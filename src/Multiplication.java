package com.example.calculator.ast;

/**
 * Represents multiplication using the '*' symbol.
 */
public class Multiplication extends Binary {
    
    /**
     * Creates a new multiplication
     * @param lhs  left hand side of the multiplication
     * @param rhs  right hand side of the multiplication
     */
    public Multiplication(SymbolicExpression lhs, SymbolicExpression rhs) {
        super(lhs, rhs);
    }

    /**
     * Gets multiplication operator representation.
     * @return  text representation of multiplication operator
     */
    @Override
    public String getName() {
        return "*";
    }

    /**
     * Checks if an object represents multiplication.
     * @param other  object to check
     * @return  true if the given object is an multiplication, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Multiplication) {
            return this.equals((Multiplication) other);
        } else {
            return false;
        }
    }

    /**
     * Checks if two multiplications are equal. 
     * @param other  second multiplication to check
     * @return  true if the multiplications are equal, false otherwise
     */
    public boolean equals(Multiplication other) {
        if (!super.operatorEquals(other)) {
            return false;
        }
        if (this.getlhs().equals(other.lhs) && (this.getrhs().equals(other.rhs))) {
            return true;
        }
        if (this.getlhs().equals(other.rhs) && this.getrhs().equals(other.lhs)) {
            return true;
        }
        return false;
    }

    /**
     * Evaluates a multiplication.
     * @param lhs  left hand side of the multiplication
     * @param rhs  right hand side of the multiplication
     * @return  a number (constant) if both sides are numbers, otherwise 
     *          a new multiplication 
     */
    private SymbolicExpression eval(SymbolicExpression lhs, SymbolicExpression rhs) {
        if (lhs.isConstant() && rhs.isConstant()) {
            return new Constant(lhs.getValue() * rhs.getValue());
        } else {
            return new Multiplication(lhs, rhs);
        }
    }

    /**
     * Evaluates a multiplication.
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
