package com.example.calculator.parser;

import java.io.IOException;

import com.example.calculator.ast.*;

/**
 * Tests for the calculator parser.
 */
public class ParserTest {
    
    private static CalculatorParser p;
    
    /**
     * Test entry point that will run the complete suite.
     * @param args  ignored
     */
    public static void main(String[] args) {
        runTestSuite();
    }

    /**
     * Runs the complete test suite.
     */
    public static void runTestSuite() {
        p = new CalculatorParser();

        System.out.println("*** Parser Test ***\n");
        System.out.println("Starting Parser Test\n");
        
        testFirstExpressionSequence();
        testFirstEvaluationSequence();
        testSecondExpressionSequence();
        testSecondEvaluationSequence();
        testThirdExpressionSequence();
        testThirdEvaluationSequence();
        testFourthExpressionSequence();
        testFourthEvaluationSequence();
        
        System.out.println("\nFinished Parser Test\n");
    }

    /**
     * Tests a set of evaluations taken from the assignment instructions.
     */
    public static void testFirstEvaluationSequence() {
        System.out.println("First Evaluation Sequence\n");

        Environment vars = new Environment();
        
        // 3=x
        Constant c1 = new Constant(3);
        Variable v1 = new Variable("x");
        Assignment a1 = new Assignment(c1, v1);
        testEvaluation(c1, "3=x", vars);

        // x+y
        Variable v2 = new Variable("y");
        Addition ad1 = new Addition(v1, v2);
        testEvaluation(new Addition(c1, v2), "x+y", vars);

        // z+2=y
        Variable v3 = new Variable("z");
        Constant c2 = new Constant(2);
        Addition ad2 = new Addition(v3, c2);
        Assignment a2 = new Assignment(ad2, v2);
        testEvaluation(ad2, "z+2=y", vars);

        // 3=z
        Assignment a3 = new Assignment(c1, v3);
        testEvaluation(c1, "3=z", vars);

        // y=f
        Variable v4 = new Variable("f");
        Assignment a4 = new Assignment(v2, v4);
        testEvaluation(ad2, "y=f", vars);

        //f+z*2
        Multiplication m1 = new Multiplication(v3, c2);
        Addition ad3 = new Addition(v4, m1);
        Addition ad4 = new Addition(ad2, new Constant(6));
        testEvaluation(ad4, "f+z*2", vars);

        //z+x=g
        Variable v5 = new Variable("g");
        Addition ad5 = new Addition(v3, v1);
        Assignment a5 = new Assignment(ad5, v5);
        testEvaluation(new Constant(6), "z+x=g", vars);

        //g
        testEvaluation(new Constant(6), "g", vars);

        //f
        testEvaluation(ad2, "f", vars);

        //z
        testEvaluation(new Constant(3), "z", vars);

        //(f+z*z)
        Multiplication m2 = new Multiplication(v3, v3);
        Addition ad6 = new Addition(v4, m2);
        testEvaluation(new Addition(new Addition(v3, new Constant(2)), new Constant(9)), "(f+z*z)", vars);

        //vars
        //testEvaluationException(Vars.instance(), vars);

        //quit
        //testEvaluationException(Quit.instance(), vars);
        System.out.println("\nFirst Evaluation Sequence Finished\n");
    }

    /**
     * Tests a set of expressions taken from the assignment instructions.
     */
    public static void testFirstExpressionSequence() {
        System.out.println("First Expression Sequence\n");
        
        Environment vars = new Environment();
        
        testParser("3.0 = x", "3=x", vars);
        testParser("x + y", "x+y", vars);
        testParser("z + 2.0 = y", "z+2=y", vars);
        testParser("3.0 = z", "3=z", vars);
        testParser("y", "y", vars);
        testParser("y = f", "y=f", vars);
        testParser("f + z * 2.0", "f+z*2", vars);
        testParser("z + x = g", "z+x=g", vars);
        testParser("g", "g", vars);
        testParser("f", "f", vars);
        testParser("z", "z", vars);
        testParser("f + z * z", "(f+z*z)", vars);
        testParser("vars", "vars", vars);
        testParser("quit", "quit", vars);
        testParser("clear", "clear", vars);

        System.out.println("\nFirst Expression Sequence Finished\n");
    }

    /**
     * Tests a set of expressions taken from the assignment instructions.
     */
    public static void testSecondExpressionSequence() {
        System.out.println("\nSecond Expression Sequence\n");
        
        Environment vars = new Environment();
        
        //2*(5-2)
        testParser("2.0 * (5.0 - 2.0)", "2*(5-2)", vars);
        //1/(2+8)=a
        testParser("1.0 / (2.0 + 8.0) = a", "1/(2+8)=a", vars);
        //2=x=y
        testParser("2.0 = x = y", "2=x=y", vars);
        //(x+y=z) - (z-1)*(1=x)
        testParser("x + y = z - (z - 1.0) * 1.0 = x", "(x+y=z) - (z-1)*(1=x)", vars);
        //-sin(exp(a*a*10))
        testParser("-sin(exp(a * a * 10.0))", "-sin(exp(a*a*10))", vars);
        //- - -x
        testParser("---x", "- - -x", vars);
        
        System.out.println("\nSecond Expression Sequence Finished\n");
    }

    /**
     * Tests a set of evaluations taken from the assignment instructions.
     */
    public static void testSecondEvaluationSequence() {
        System.out.println("\nSecond Evaluation Sequence\n");

        Environment vars = new Environment();
        
        //2*(5-2)
        Constant c1 = new Constant(2);
        Constant c2 = new Constant(5);
        Subtraction s1 = new Subtraction(c2, c1);
        Multiplication m1 = new Multiplication(c1, s1);
        testEvaluation(new Constant(6), "2*(5-2)", vars);

        //1/(2+8)=a
        Constant c3 = new Constant(1);
        Addition ad1 = new Addition(new Constant(2), new Constant(8));
        Division div1 = new Division(c3, ad1);
        Variable v1 = new Variable("a");
        Assignment as1 = new Assignment(div1, v1);
        testEvaluation(new Constant(0.1), "1/(2+8)=a", vars);

        //2=x=y
        Variable v2 = new Variable("x");
        Variable v3 = new Variable("y");
        Assignment as2 = new Assignment(c1, v2);
        Assignment as3 = new Assignment(as2, v3);
        testEvaluation(c1, "2=x=y", vars);

        //(x+y=z) - (z-1)*(1=x)
        Addition ad2 = new Addition(v2, v3);
        Variable v4 = new Variable("z");
        Assignment as4 = new Assignment(ad2, v4);
        Constant c4 = new Constant(1);
        Subtraction s2 = new Subtraction(v4, c4);
        Assignment as5 = new Assignment(c4, v2);
        Multiplication m2 = new Multiplication(s2, as5);
        Subtraction s3 = new Subtraction(as4, m2);
        testEvaluation(c4, "(x+y=z) - (z-1)*(1=x)", vars);

        //-sin(exp(a*a*10))
        Constant c5 = new Constant(10);
        Multiplication m3 = new Multiplication(v1, c5);
        Multiplication m4 = new Multiplication(v1, m3);
        Exp exp1 = new Exp(m4);
        Sin sin1 = new Sin(exp1);
        Negation n1 = new Negation(sin1);
        testEvaluation(new Constant(-0.8935409432921488), "-sin(exp(a*a*10))", vars);

        //- - -x
        Negation n2 = new Negation(v2);
        testEvaluation(new Constant(-1), "- - -x", vars);
        
        System.out.println("\nSecond Evaluation Sequence Finished\n");
    }

    /**
     * Tests a set of expressions taken from the assignment instructions.
     */
    public static void testThirdExpressionSequence() {
        System.out.println("Third Expression Sequence\n");
        
        Environment vars = new Environment();

        //b
        testParser("b", "b", vars);

        //1 = a
        testParser("1.0 = a", "1=a", vars);

        //b + a = c
        testParser("b + a = c", "b+a=c", vars);

        //2 = b
        testParser("2.0 = b", "2=b", vars);

        //c 
        testParser("c", "c", vars);

        //2*x = y
        testParser("2.0 * x = y", "2*x=y", vars);

        //1/0
        testParser("1.0 / 0.0", "1/0", vars);

        System.out.println("\nThird Expression Sequence Finished\n");
    }

    /**
     * Tests a set of evaluations taken from the assignment instructions.
     */
    public static void testThirdEvaluationSequence() {
        System.out.println("Third Evaluation Sequence\n");
        
        Environment vars = new Environment();

        //b
        Variable v1 = new Variable("b");
        testEvaluation(v1, "b", vars);

        //1 = a
        Constant c1 = new Constant(1);
        Variable v2 = new Variable("a");
        Assignment as1 = new Assignment(c1, v2);
        testEvaluation(c1, "1=a", vars);

        //b + a = c
        Variable v3 = new Variable("c");
        Addition ad1 = new Addition(v1, v2);
        Assignment as2 = new Assignment(ad1, v3);
        testEvaluation(new Addition(v1, c1), "b+a=c", vars);

        //2 = b
        Constant c2 = new Constant(2);
        Assignment as3 = new Assignment(c2, v1);
        testEvaluation(c2, "2=b", vars);

        //c 
        testEvaluation(new Addition(v1, c1), "c", vars);

        //2*x = y
        Variable v4 = new Variable("x");
        Multiplication m1 = new Multiplication(c2, v4);
        Variable v5 = new Variable("y");
        Assignment as4 = new Assignment(m1, v5);
        testEvaluation(m1, "2*x=y", vars);

        //1/0
        testEvaluation(new Constant((1.0/0.0)), "1/0", vars);

        System.out.println("Third Evaluation Sequence Finished\n");
    }

    /**
     * Tests a set of expressions.
     */
    public static void testFourthExpressionSequence() {
        System.out.println("Fourth Expression Sequence\n");
        
        Environment vars = new Environment();
        
        //pi
        testParser("pi", "pi", vars);

        //Answer + Answer
        testParser("Answer + Answer", "Answer + Answer", vars);

        //3**x
        testSyntaxErrorException("3**x", vars);

        //1=quit
        testIllegalAssignmentErrorException("1=quit", vars);

        //1=pi
        testIllegalAssignmentErrorEvaluationException("1=pi", vars);

        System.out.println("Fourth Expression Sequence Finished\n");
    }

    /**
     * Tests a set of evaluations.
     */
    public static void testFourthEvaluationSequence() {
        System.out.println("Fourth Evaluation Sequence\n");
        
        Environment vars = new Environment();

        //pi
        testEvaluation(new Constant(Math.PI), "pi", vars);

        //Answer + Answer
        testEvaluation(new Constant(84), "Answer + Answer", vars);

        System.out.println("Fourth Evaluation Sequence Finished\n");
    }

    /**
     * Tests parsing of expressions and evaluation of results.
     * @param expected  the expected output
     * @param inputString  the expression to be tested
     * @param vars  stored variables
     */
    public static void testParser(String expected, String inputString, Environment vars) {
        p.parse(inputString);
        
        try {
            SymbolicExpression result = p.statement();
            System.out.println("Testing expected print " + expected + " against " + result.toString());
            assert expected.equals(result.toString());
        } catch(SyntaxErrorException e) {
            System.out.println(e.getMessage());
            assert e.getMessage().equals(expected);
        } catch(IOException e) {
            System.err.println("IO Exception!");
        }
    }

    /**
     * Tests if the parser throw SyntaxErrorException upon syntactical errors.
     * @param e  the expression to be tested
     * @param vars  stored variables
     */
    public static void testSyntaxErrorException(String inputString, Environment vars) {
        p.parse(inputString);
        
        try {
            System.out.print("Testing expected syntax error exception for input '" + inputString + "'");
            SymbolicExpression result = p.statement();
        } catch (SyntaxErrorException e) {
            System.out.println(" - passed");
        } catch (IOException e) {
            System.err.println("IO Exception!");
        }
    }

    /**
     * Tests if the parser throw testIllegalAssignmentErrorException when trying to 
     * redefined "protected" variables.
     * @param e  the expression to be tested
     * @param vars  stored variables
     */
    public static void testIllegalAssignmentErrorException(String inputString, Environment vars) {
        p.parse(inputString);
        
        try {
            System.out.print("Testing expected illegal assignment error exception for input '" + inputString + "'");
            SymbolicExpression result = p.statement();
        } catch (IllegalAssignmentException e) {
            System.out.println(" - passed");
        } catch (SyntaxErrorException e) {
            System.err.println("SyntaxErrorException: " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("IO Exception!");
        }
    }

    /**
     * Tests if the parser throw testIllegalAssignmentErrorException when trying to 
     * redefined "protected" variables during evaluation.
     * @param e  the expression to be tested
     * @param vars  stored variables
     */
    public static void testIllegalAssignmentErrorEvaluationException(String inputString, Environment vars) {
        p.parse(inputString);
        
        try {
            System.out.print("Testing expected illegal assignment during evaluation error exception for input '" + inputString + "'");
            SymbolicExpression result = p.statement();
            result = result.eval(vars);
        } catch (IllegalAssignmentException e) {
            System.out.println(" - passed");
        } catch (SyntaxErrorException e) {
            System.err.println("SyntaxErrorException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception!");
        }
    }

    /**Tests evaluation of expressions.
     * @param expected  the expected evaluated result
     * @param e  the expression to be tested
     * @param vars  stored variables
     */
    public static void testEvaluation(SymbolicExpression expected, String inputString, Environment vars) {
        p.parse(inputString);
        
        try {
            SymbolicExpression result = p.statement();
            result = result.eval(vars);
            System.out.println("Testing expected evaluation " + expected + " against " + result.toString());
            assert expected.equals(result);
        } catch (SyntaxErrorException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception!");
        }
    }


}