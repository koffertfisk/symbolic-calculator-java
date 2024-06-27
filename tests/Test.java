package com.example.calculator;

import com.example.calculator.parser.*;

import com.example.calculator.ast.*;

/**
 * Calculator test suite
 */
public class Test {

    /**
     * Test entry point that will run the complete suite.
     * @param args  ignored
     */
    public static void main(String[] args) {
        System.out.println("*** Calculator Test ***\n");
        System.out.println("Starting Main Test\n");
        
        ParserTest parserTest = new ParserTest();
        parserTest.runTestSuite();

        ASTTest.runTestSuite();

        System.out.println("\nFinished Main Test\n");
    }

}