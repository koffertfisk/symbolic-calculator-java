package com.example.calculator.ast;

/**
 * Represents variable assignment using the '=' symbol.
 */
public class Assignment extends Binary {
    
    /**
     * Creates a new assignment
     * @param lhs  left hand side of the assignment
     * @param rhs  right hand side of the assignment
     */
    public Assignment(SymbolicExpression lhs, SymbolicExpression rhs) {
        super(lhs, rhs);
    }

    /**
     * Get assignment operator presentation.
     * @return  text representation of assignment operator
     */
    @Override
    public String getName() {
        return "=";
    }

    /**
     * Checks if an object represents assignment.
     * @param other  object to check
     * @return  true if the given object is an assignment, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Assignment) {
            return this.equals((Assignment) other);
        } else {
            return false;
        }
    }

    /**
     * Gets assignment priority for determining order of operation
     * @return  fixed priority of 50
     */
    @Override
    public int getPriority() {
        return 50;
    }

    /**
     * Checks if two assignments are equal. 
     * @param other  second assignment to check
     * @return  true if the assignments are equal, false otherwise
     */
    public boolean equals(Assignment other) {
        if (!super.operatorEquals(other)) {
            return false;
        }
        if (this.getlhs().equals(other.lhs) && (this.getrhs().equals(other.rhs))) {
            return true;
        }
        return false;
    }

    /**
     * Evaluates an assignment if it is considered legal and stores the result in
     * the provided variable environment.
     * @param vars  stored variables
     * @return  a symbolic expression depending on the reducability of the expression
     * @throws IllegalAssignmentException  in case a variable cannot the redefined
     */
    @Override
    public SymbolicExpression eval(Environment vars) {
        if (this.getrhs().isConstant()) {
            throw new IllegalAssignmentException("cannot redefine named constant");
        } else if (this.getlhs().isConstant()) {
            vars.put((Variable) this.getrhs(), this.getlhs());
        } else {
            vars.put((Variable) this.getrhs(), this.getlhs().eval(vars));
        }
        
        return vars.get(this.getrhs());
    }

}
