package com.example.calculator.ast;

/**
 * Represents a 'vars' command for showing stored variables.
 */
public class Vars extends Command {

    private static final Vars theInstance = new Vars();

    /**
     * Creates a singleton vars command
     */
    private Vars() {}
    
    /**
     * Gets the singleton vars instance.
     * @return  the single vars instance
     */
    public static Vars instance() {
        return theInstance;
    }

    /**
     * Gets the vars command name.
     * @return  the name 'vars'
     */
    @Override
    public String getName() {
        return "vars";
    }

}