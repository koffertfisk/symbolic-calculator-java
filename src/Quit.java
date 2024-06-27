package com.example.calculator.ast;

/**
 * Represents a 'quit' command for exiting application.
 */
public class Quit extends Command {

    private static final Quit theInstance = new Quit();
    
    /**
     * Creates a singleton quit command
     */
    private Quit() {}
    
    /**
     * Gets the quit command name.
     * @return  the name 'quit'
     */
    @Override
    public String getName() {
        return "quit";
    }

    /**
     * Gets the singleton quit instance.
     * @return  the single quit instance
     */
    public static Quit instance() {
        return theInstance;
    }
    
}