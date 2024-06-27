package com.example.calculator.parser;

import java.io.IOException;
import java.util.Scanner;

import com.example.calculator.ast.*;

/**
 * Minimal interface for interacting with the parser.
 */
public class ParserDriver {
    
    /**
     * The main method to run the parser.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CalculatorParser p = new CalculatorParser();

        System.out.println("Welcome to the parser!");
        System.out.print("Please enter an expression: ");
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        Environment vars = new Environment();
        p.parse(inputString);

        try {
            SymbolicExpression result = p.statement();
            System.out.println("result: " + result);
            if (result.isCommand()) {
                if (result.getName().equals("quit")) {
                    System.out.println("\nExiting application\n");
                    System.out.println("Bye!");
                } else if (result.getName().equals("vars")) {
                    System.out.println(vars);
                } else {
                    vars.clear();
                }
            } else {
                SymbolicExpression evaluated = result.eval(vars);
                System.out.println(evaluated);
                vars.put(new Variable("ans"), evaluated);
            }
        } catch(SyntaxErrorException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
        scanner.close();
    }
}