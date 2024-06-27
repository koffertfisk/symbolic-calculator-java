package com.example.calculator.ast;

/**
 * Represents a 'clear' variables command.
 */
public class Clear extends Command {

    private static final Clear theInstance = new Clear();

    /**
     * Creates a singleton clear command
     */
    private Clear() {}
    
    /**
     * Gets the singleton clear instance.
     * @return  the single clear instance
     */
    public static Clear instance() {
        return theInstance;
    }

    /**
     * Gets the clear command name.
     * @return  the name 'clear'
     */
    @Override
    public String getName() {
        return "clear";
    }
}