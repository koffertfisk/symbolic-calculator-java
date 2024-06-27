package com.example.calculator.ast;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Represents stored variables.
 */
public class Environment extends HashMap<Variable, SymbolicExpression> {

    /**
     * Pretty printed text representation of the stored variables. 
     * Inspired by https://stackoverflow.com/questions/10120273/pretty-print-a-map-in-java
     * @return  string representation of the stored variables
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<Variable, SymbolicExpression>> iter = this.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Variable, SymbolicExpression> entry = iter.next();
            sb.append(entry.getKey());
            sb.append(" = ").append(entry.getValue());
            if (iter.hasNext()) {
                sb.append("\n");
            }
        }
        return sb.toString();
    } 
}