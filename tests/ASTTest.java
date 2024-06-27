package com.example.calculator.ast;

/**
 * Tests for the Abstract Syntax Tree (AST).
 */
public class ASTTest {

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
        System.out.println("*** AST Test ***\n");
        System.out.println("Starting AST Test\n");
        
        testPrintingSequence();
        testFirstEvaluationSequence();
        testSecondEvaluationSequence();
        testThirdEvaluationSequence();
        testFourthEvaluationSequence();

        System.out.println("\nFinished AST Test\n");
    }

    public static void testEvaluationSequence() {
        System.out.println("\nStarting Evaluation Test Sequence\n");
        System.out.println("\nEvaluation Test Sequence Finished\n");
    }
    
    /**
    * Tests a set of printing
    */
    public static void testPrintingSequence() {
        System.out.println("\nStarting Printing Test Sequence\n");

        //1/(2+8)=a
        Constant c3 = new Constant(1);
        Addition ad1 = new Addition(new Constant(2), new Constant(8));
        Division div1 = new Division(c3, ad1);
        Variable v1 = new Variable("a");
        Assignment as1 = new Assignment(div1, v1);
        testPrinting("1.0 / (2.0 + 8.0) = a", as1);

        //2=x=y
        Variable v2 = new Variable("x");
        Variable v3 = new Variable("y");
        Assignment as2 = new Assignment(new Constant(2), v2);
        Assignment as3 = new Assignment(as2, v3);
        testPrinting("2.0 = x = y", as3);

        //-sin(exp(a*a*10))
        Constant c5 = new Constant(10);
        Multiplication m3 = new Multiplication(v1, c5);
        Multiplication m4 = new Multiplication(v1, m3);
        Exp exp1 = new Exp(m4);
        Sin sin1 = new Sin(exp1);
        Negation n1 = new Negation(sin1);
        testPrinting("-sin(exp(a * a * 10.0))", n1);

        //- - -x
        Negation n2 = new Negation(v2);
        testPrinting("-x", n2);

        //pi
        testPrinting("pi", new NamedConstant("pi", Math.PI));

        //1=pi
        Assignment as4 = new Assignment(new Constant(1), new NamedConstant("pi", Math.PI));
        testIllegalAssignmentException(as4, new Environment());

        //vars
        testPrinting("vars", Vars.instance());

        System.out.println("\nPrinting Test Sequence Finished\n");
    }

    /**
     * Tests a set of evaluations taken from the assignment instructions.
     */
    public static void testFirstEvaluationSequence() {
        Environment vars = new Environment();
        
        // 3=x
        Constant c1 = new Constant(3);
        Variable v1 = new Variable("x");
        Assignment a1 = new Assignment(c1, v1);
        testEvaluating(c1, a1, vars);

        // x+y
        Variable v2 = new Variable("y");
        Addition ad1 = new Addition(v1, v2);
        testEvaluating(new Addition(c1, v2), ad1, vars);

        // z+2=y
        Variable v3 = new Variable("z");
        Constant c2 = new Constant(2);
        Addition ad2 = new Addition(v3, c2);
        Assignment a2 = new Assignment(ad2, v2);
        testEvaluating(ad2, a2, vars);

        // 3=z
        Assignment a3 = new Assignment(c1, v3);
        testEvaluating(c1, a3, vars);

        // y=f
        Variable v4 = new Variable("f");
        Assignment a4 = new Assignment(v2, v4);
        testEvaluating(ad2, a4, vars);

        //f+z*2
        Multiplication m1 = new Multiplication(v3, c2);
        Addition ad3 = new Addition(v4, m1);
        Addition ad4 = new Addition(ad2, new Constant(6));
        testEvaluating(ad4, ad3, vars);

        //z+x=g
        Variable v5 = new Variable("g");
        Addition ad5 = new Addition(v3, v1);
        Assignment a5 = new Assignment(ad5, v5);
        testEvaluating(new Constant(6), a5, vars);

        //g
        testEvaluating(new Constant(6), v5, vars);

        //f
        testEvaluating(ad2, v4, vars);

        //z
        testEvaluating(new Constant(3), v3, vars);

        //(f+z*z)
        Multiplication m2 = new Multiplication(v3, v3);
        Addition ad6 = new Addition(v4, m2);
        testEvaluating(new Addition(new Addition(v3, new Constant(2)), new Constant(9)), ad6, vars);

        //vars
        testEvaluationException(Vars.instance(), vars);

        //quit
        testEvaluationException(Quit.instance(), vars);
    }

    /**
     * Tests a set of evaluations taken from the assignment instructions.
     */
    public static void testSecondEvaluationSequence() {
        Environment vars = new Environment();
        
        //2*(5-2)
        Constant c1 = new Constant(2);
        Constant c2 = new Constant(5);
        Subtraction s1 = new Subtraction(c2, c1);
        Multiplication m1 = new Multiplication(c1, s1);
        testEvaluating(new Constant(6), m1, vars);

        //1/(2+8)
        Constant c3 = new Constant(1);
        Addition ad1 = new Addition(new Constant(2), new Constant(8));
        Division div1 = new Division(c3, ad1);
        Variable v1 = new Variable("a");
        Assignment as1 = new Assignment(div1, v1);
        testEvaluating(new Constant(0.1), as1, vars);

        //2=x=y
        Variable v2 = new Variable("x");
        Variable v3 = new Variable("y");
        Assignment as2 = new Assignment(c1, v2);
        Assignment as3 = new Assignment(as2, v3);
        testEvaluating(c1, as3, vars);

        //(x+y=z) - (z-1)*(1=x)
        Addition ad2 = new Addition(v2, v3);
        Variable v4 = new Variable("z");
        Assignment as4 = new Assignment(ad2, v4);
        Constant c4 = new Constant(1);
        Subtraction s2 = new Subtraction(v4, c4);
        Assignment as5 = new Assignment(c4, v2);
        Multiplication m2 = new Multiplication(s2, as5);
        Subtraction s3 = new Subtraction(as4, m2);
        testEvaluating(c4, s3, vars);

        //-sin(exp(a*a*10))
        Constant c5 = new Constant(10);
        Multiplication m3 = new Multiplication(v1, c5);
        Multiplication m4 = new Multiplication(v1, m3);
        Exp exp1 = new Exp(m4);
        Sin sin1 = new Sin(exp1);
        Negation n1 = new Negation(sin1);
        testEvaluating(new Constant(-0.8935409432921488), n1, vars);

        //- - -x
        Negation n2 = new Negation(v2);
        testEvaluating(new Constant(-1), n2, vars);
    }

    /**
     * Tests a set of evaluations taken from the assignment instructions.
     */
    public static void testThirdEvaluationSequence() {
        Environment vars = new Environment();

        //b
        Variable v1 = new Variable("b");
        testEvaluating(v1, v1, vars);

        //1 = a
        Constant c1 = new Constant(1);
        Variable v2 = new Variable("a");
        Assignment as1 = new Assignment(c1, v2);
        testEvaluating(c1, as1, vars);

        //b + a = c
        Variable v3 = new Variable("c");
        Addition ad1 = new Addition(v1, v2);
        Assignment as2 = new Assignment(ad1, v3);
        testEvaluating(new Addition(v1, c1), as2, vars);

        //2 = b
        Constant c2 = new Constant(2);
        Assignment as3 = new Assignment(c2, v1);
        testEvaluating(c2, as3, vars);

        //c 
        testEvaluating(new Addition(v1, c1), v3, vars);

        //2*x = y
        Variable v4 = new Variable("x");
        Multiplication m1 = new Multiplication(c2, v4);
        Variable v5 = new Variable("y");
        Assignment as4 = new Assignment(m1, v5);
        testEvaluating(m1, as4, vars);

        //1/0
        testEvaluating(new Constant((1.0/0.0)), new Division(new Constant(1), new Constant(0)), vars);
    }

    /**
     * Tests forbidden reassignments.
     */
    public static void testFourthEvaluationSequence() {
        Environment vars = new Environment();
        testIllegalAssignmentException(new Assignment(new Constant(1), new NamedConstant("pi", Math.PI)), vars);
        testIllegalAssignmentException(new Assignment(new Constant(1), new Variable("pi")), vars);
        testEvaluating(new Constant(Math.PI), new NamedConstant("pi", Math.PI), vars);
    }

    /**
     * Tests printing of expressions.
     * @param expected  expected string representation of an expression.
     * @param e  the expression to be tested
     */
    public static void testPrinting(String expected, SymbolicExpression e) {
        System.out.println("Testing expected print " + expected + " against " + e.toString());
        assert expected.equals(e.toString());
    }
    
    /**Tests evaluation of expressions.
     * @param expected  the expected evaluated result
     * @param e  the expression to be tested
     * @param vars  stored variables
     */
    public static void testEvaluating(SymbolicExpression expected, SymbolicExpression e, Environment vars) {
        SymbolicExpression r = e.eval(vars);
        if (r.equals(expected)) {
            System.out.println("Passed: " + e);
        } else {
            System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
        }
    }

    /**
     * Tests if evaluations throw EvaluationException when they should.
     * @param e  the expression to be tested
     * @param vars  stored variables
     */
    public static void testEvaluationException(SymbolicExpression e, Environment vars) {
        try {
            SymbolicExpression r = e.eval(vars);
        } catch (EvaluationException ex) {
            System.out.println("EvaluationException test passed with message: " + ex.getMessage());
        }
    }

    /**
     * Tests if evaluations throw IllegalAssignmentException when trying to redine "protected" varaibles
     * @param e  the expression to be tested
     * @param vars  stored variables
     */
    public static void testIllegalAssignmentException(SymbolicExpression e, Environment vars) {
        try {
            SymbolicExpression r = e.eval(vars);
        } catch (IllegalAssignmentException ex) {
            System.out.println("IllegalAssignmentException test passed with message: " + ex.getMessage());
        }
    }

}