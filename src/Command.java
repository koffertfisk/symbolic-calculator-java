package com.example.calculator.ast;

/**
 * Represents a command.
 */
public abstract class Command extends SymbolicExpression {

    /** 
     * Evalues a command
     * @throws EvaluationException  since evaluation is not supported for commands
    */
    @Override
    public SymbolicExpression eval(Environment vars) throws EvaluationException {
        throw new EvaluationException("eval() not supported for commands");
    }

    /**
     * Checks whether an instance is a command.
     * @return  true for all commands
     */
    @Override
    public boolean isCommand() {
        return true;
    }

    /**        
    * Check whether an object represents a command
    * @throws RuntimeException since equals is not supported for commands
    */
    @Override
    public boolean equals(Object other) throws RuntimeException {
        throw new RuntimeException("equals() not supported for commands");
    }

    /**
     * Text representation i.e. the name for a command.
     * @return  string reprentation of a command
     */
    @Override
    public String toString() {
        return this.getName();
    }

}