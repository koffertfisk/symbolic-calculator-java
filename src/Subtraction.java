package com.example.calculator.ast;

/** 
 * Represents subtraction using the '-' symbol.
 */
public class Subtraction extends Binary {
    
    /**
     * Creates a new subtraction.
     * @param lhs  left hand side of the subtraction
     * @param rhs  right hand side of the subtraction
     */
    public Subtraction(SymbolicExpression lhs, SymbolicExpression rhs) {
        super(lhs, rhs);
    }

    /**
     * Gets subtraction operator representation.
     * @return  text representation of subtraction operator
     */
    @Override
    public String getName() {
        return "-";
    }

    /**
     * Checks if an object represents subtraction.
     * @param other  object to check
     * @return  true if the given object is an subtraction, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Subtraction) {
            return this.equals((Subtraction) other);
        } else {
            return false;
        }
    }

    /**
     * Checks if two subtractions are equal. 
     * @param other  second subtraction to check
     * @return  true if the subtractions are equal, false otherwise
     */
    public boolean equals(Subtraction other) {
        if (!super.operatorEquals(other)) {
            return false;
        }
        if (this.getlhs().equals(other.lhs) && (this.getrhs().equals(other.rhs))) {
            return true;
        }
        return false;
    }

    /**
     * Gets subtraction priority for determining order of operation
     * @return  fixed priority of 50
     */
    @Override
    public int getPriority() {
        return 50;
    }

    /**
     * Evaluates a subtraction.
     * @param lhs  left hand side of the subtraction
     * @param rhs  right hand side of the subtraction
     * @return  a number (constant) if both sides are numbers, otherwise 
     *          a new subtraction 
     */
    private SymbolicExpression eval(SymbolicExpression lhs, SymbolicExpression rhs) {
        if (lhs.isConstant() && rhs.isConstant()) {
            return new Constant(lhs.getValue() - rhs.getValue());
        } else {
            return new Subtraction(lhs, rhs);
        }
    }

    /**
     * Evaluates a subtraction.
     * @param vars  stored variables
     * @return  a symbolic expression depending on the reducability of the expression
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        if (this.getlhs().isConstant() && this.getrhs().isConstant()) {
            return this.eval(lhs, rhs);
        } 
        else if (this.getlhs().isConstant() && !this.getrhs().isConstant()) {
            return this.eval(this.getlhs(), this.getrhs().eval(vars));
        } else if (!this.getlhs().isConstant() && this.getrhs().isConstant()) {
            return this.eval(this.getlhs().eval(vars), this.getrhs());
        }
        return this.eval(this.getlhs().eval(vars), this.getrhs().eval(vars));
    }

}
