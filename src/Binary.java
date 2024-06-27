package com.example.calculator.ast;

/**
 * Represents a binary operation which consist of a left and right hand side.
 */
public abstract class Binary extends SymbolicExpression
{
    final SymbolicExpression lhs, rhs;

    /**
     * Creates a new binary operation.
     * @param lhs  left hand side of the binary operation
     * @param rhs  right hand side of the binary operation
     */
    public Binary(SymbolicExpression lhs, SymbolicExpression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Gets left hand side of binary operation.
     * @return  A symbolic expression representing left hand side
     */
    public SymbolicExpression getlhs() {
        return this.lhs;
    }

    /**
     * Gets right hand side of binary operation.
     * @return  A symbolic expression representing left hand side
     */
    public SymbolicExpression getrhs() {
        return this.rhs;
    }

    /**
     * Check if two binary operations are equal based on their name.
     * @param other  second binary operation to check
     * @return  true if the two are equal, false otherwise
     */
    public boolean operatorEquals(Binary other) {
        return (this.getName().equals(other.getName()));
    }

    /**
     * Text representation of a binary operation with respect to the order of
     * operation based on the priorities of the two sides (i.e. left and right).
     * @return  string representation of the binary operation
     */
    @Override
    public String toString() {
        if (this.getlhs().getPriority() < this.getPriority()) {
            return "(" + this.getlhs().toString() + ")" + " " + this.getName() + " " + this.getrhs().toString();
        } else if (this.getrhs().getPriority() < this.getPriority()) {
            return this.getlhs().toString() + " " + this.getName() + " " + "(" + this.getrhs().toString() + ")";
        }
        return this.getlhs().toString() + " " + this.getName() + " " + this.getrhs().toString();

    }

}