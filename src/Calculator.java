package com.example.calculator;

/** 
 * Symbolic calculator for processing and evaluation of mathematical expressions.
 * 
 * @author Marcus Enderskog
 * @version 1.0
 * @since 2020-04-09
 */

import java.io.IOException;

import com.example.calculator.ast.*;
import com.example.calculator.parser.*;

/**
 * Main application context.
 */

public class Calculator
{
    /**
     * Application entry point where users can feed statements at the ? prompt and 
     * expect an evaluated result. Some basic statistics are shown upon exiting the application.
     * Major syntax errors and illegal operations will encourage users to correct their 
     * input and try again.
     * @param args  ignored
     */
    public static void main(String[] args) {
        boolean run = true;
        final CalculatorParser p = new CalculatorParser();
        Environment vars = new Environment();
        int expressionCount = 0;
        int evaluationCount = 0;
        int fullevaluationCount = 0;

        System.out.println("Welcome to the Symbolic Calculator!");

        do {
            System.out.print("? ");
            String input = System.console().readLine();
            try {
                p.parse(input);
                SymbolicExpression result = p.statement();
                if (result.isCommand()) {
                    if (result.getName().equals("quit")) {
                        System.out.println("\nExiting application\n");
                        System.out.println("Number of expressions entered: " + expressionCount + "\n");
                        System.out.println("Number of successful evaluations: " + evaluationCount + "\n");
                        System.out.println("Number of fully reduced evaluations: " + fullevaluationCount + "\n");
                        System.out.println("Bye!");
                        run = false;
                    } else if (result.getName().equals("vars")) {
                        System.out.println(vars);
                    } else {
                        vars.clear();
                    }
                } else {
                    expressionCount += 1;
                    try {
                        SymbolicExpression evaluated = result.eval(vars);
                        System.out.println(evaluated);
                        if (!result.equals(evaluated)) {
                            fullevaluationCount += 1;
                        }
                        vars.put(new Variable("ans"), evaluated);
                        evaluationCount += 1;

                    } catch (EvaluationException e) {
                        System.out.println("*** Evaluation Error: " + e.getMessage() + "\n");
                    }
                }

            } catch (IllegalAssignmentException e) {
                System.out.println("*** Error: " + e.getMessage() + "\n");
                System.out.println("Try again.\n");
            } catch (SyntaxErrorException e) {
                System.out.println("*** Syntax Error: " + e.getMessage() + "\n");
                System.out.println("Try again.\n");
            } catch (IOException e) {
                System.out.println(e.getMessage() + "\n");
                System.out.println("Try again.\n");
            }
        }
        while (run);
    }
}