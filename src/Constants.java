package com.example.calculator.parser;

import java.util.HashMap;

import com.example.calculator.ast.NamedConstant;

/**
 * Represents "protected" named constants.
 */
public class Constants {
    
    /**
     * A map of named constants.
     */
    public static final HashMap<String, NamedConstant> namedConstants = new HashMap<>();

    static {
        Constants.namedConstants.put("pi", new NamedConstant("pi", Math.PI));
        Constants.namedConstants.put("e", new NamedConstant("e", Math.E));
        Constants.namedConstants.put("L", new NamedConstant("L", 6.022140857e23));
        Constants.namedConstants.put("Answer", new NamedConstant("Answer", 42.0));
    }
}