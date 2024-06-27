package com.example.calculator.parser;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

import com.example.calculator.ast.*;

/**
 * Parser for interpretation of statements input by users based on a StreamTokenizer which
 * parses symbol-by-symbol.
 */
public class CalculatorParser {

    private StreamTokenizer st = null;

    /**
     * Creates a new parser.
     */
    public CalculatorParser() {}

    /**
     * Parses an assignment.
     * @return  a symbolic expression according to the Abstract Syntax Tree (AST)
     * @throws IOException  if the underlying StreamTokenizer does not catch a more specific error
     * @throws SyntaxErrorException  in case of syntax errors
     * @throws IllegalAssignmentException  in case users try to redefine "protected" variables
     */
    public SymbolicExpression assignment() throws IOException, SyntaxErrorException {
        SymbolicExpression result = expression();
        while (this.st.ttype == '=') {
            this.st.nextToken();
            if (this.st.ttype == StreamTokenizer.TT_WORD) {
                if (Constants.namedConstants.containsKey(this.st.sval)) {
                    result = new Assignment(result, Constants.namedConstants.get(this.st.sval));
                } else if (isKeyword()) {
                    throw new IllegalAssignmentException(this.st.sval + " cannot be redefined");
                }
                else {
                    result = new Assignment(result, new Variable(this.st.sval));
                }
                this.st.nextToken();   
            } else {
                throw new SyntaxErrorException("No identifier found");
            }
        }
        return result;
    }

    /**
     * Parses an expression.
     * @return  a symbolic expression according to the Abstract Syntax Tree (AST)
     * @throws IOException  if the underlying StreamTokenizer does not catch a more specific error
     * @throws SyntaxErrorException  in case of syntax errors
     */
    public SymbolicExpression expression() throws IOException, SyntaxErrorException {
        SymbolicExpression result = term();
        while (this.st.ttype == '+' || this.st.ttype == '-') {
            int operation = this.st.ttype;
            this.st.nextToken();
            if (this.st.ttype != StreamTokenizer.TT_EOF) {
                if (isOperator()) {
                    throw new SyntaxErrorException("unexpected operator");
                }
                if (operation == '+') {
                    result = new Addition(result, term());
                } else {
                    result = new Subtraction(result, term());
                }
            } else {
                throw new SyntaxErrorException("unexpected EOF");
            }
        }
        return result;
    }

    /**
     * Parses a term.
     * @return  a symbolic expression according to the Abstract Syntax Tree (AST)
     * @throws IOException  if the underlying StreamTokenizer does not catch a more specific error
     * @throws SyntaxErrorException  in case of syntax errors
     */
    public SymbolicExpression term() throws IOException, SyntaxErrorException {
        SymbolicExpression result = factor();
        while (this.st.ttype == '*' || this.st.ttype == '/') {
            int operation = this.st.ttype;
            this.st.nextToken();
            if (this.st.ttype != StreamTokenizer.TT_EOF) {
                if (isOperator()) {
                    throw new SyntaxErrorException("unexpected operator");
                }
                if (operation == '*') {
                    result = new Multiplication(result, factor());
                } else {
                    result = new Division(result, factor());
                }
            } else {
                throw new SyntaxErrorException("unexpected EOF");
            }
        }
        return result;
    }

    /**
     * Parses a parenthesized expression.
     * @return  a symbolic expression according to the Abstract Syntax Tree (AST)
     * @throws IOException  if the underlying StreamTokenizer does not catch a more specific error
     * @throws SyntaxErrorException  in case of syntax errors
     */
    public SymbolicExpression factor() throws IOException, SyntaxErrorException {
        SymbolicExpression result = null;
        if (this.st.ttype == '(') {
            this.st.nextToken();
            result = assignment();
            if (this.st.ttype == ')') {
                this.st.nextToken();
            } else {
                throw new SyntaxErrorException("expected ')'");
            }
        } else {
            result = number();
        }
        return result;
    }

    /**
     * Parses a number, variable or unary operation.
     * @return  a symbolic expression according to the Abstract Syntax Tree (AST)
     * @throws IOException  if the underlying StreamTokenizer does not catch a more specific error
     * @throws SyntaxErrorException  in case of syntax errors
     */
    public SymbolicExpression number() throws IOException, SyntaxErrorException {
        SymbolicExpression result = null;
        
        if (this.st.ttype == StreamTokenizer.TT_NUMBER) {
            result = new Constant(this.st.nval);
            this.st.nextToken();
        } else if (this.st.ttype == '-') {
            this.st.nextToken();
            result = new Negation(factor());
        } else if (isUnary()) {
            result = unary();
        } else if (this.st.ttype == StreamTokenizer.TT_WORD) {
            if (Constants.namedConstants.containsKey(this.st.sval)) {
                result = Constants.namedConstants.get(this.st.sval);
            } else {
                result = new Variable(this.st.sval);
            }
            this.st.nextToken();
        } else {
            throw new SyntaxErrorException("Unexpected factor.");
        }
        
        return result;
    }

    /**
     * Parses a unary operation.
     * @return  a symbolic expression according to the Abstract Syntax Tree (AST)
     * @throws IOException  if the underlying StreamTokenizer does not catch a more specific error
     * @throws SyntaxErrorException  in case of syntax errors
     */
    public SymbolicExpression unary() throws IOException, SyntaxErrorException {
        String unary = this.st.sval;
        SymbolicExpression result;
        this.st.nextToken();
        if (this.st.ttype == '(') {
            result = factor();
        } else {
            throw new SyntaxErrorException("'(' missing for unary");
        }
        if (unary.equals("exp"))
            return new Exp(result);
        else if (unary.equals("log"))
            return new Log(result);
        else if (unary.equals("sin"))
            return new Sin(result);
        else if (unary.equals("cos"))
            return new Cos(result);
        else
            throw new RuntimeException("no matching unary found!");
    }

    /**
     * Parses a statement.
     * @return  a symbolic expression according to the Abstract Syntax Tree (AST)
     * @throws IOException  if the underlying StreamTokenizer does not catch a more specific error
     * @throws SyntaxErrorException  in case of syntax errors
     */
    public SymbolicExpression statement() throws IOException, SyntaxErrorException {
        this.st.nextToken();
        if (this.st.ttype == StreamTokenizer.TT_EOF) {
            throw new SyntaxErrorException("Empty statement entered");
        }
        if (isCommand()) {
            return command();
        } else {
            return assignment();
        }
    }

    /**
     * Checks whether the current token is a command.
     * @return  true in case of a matching command, false otherwise
     */
    public boolean isCommand() {
        if (this.st.ttype == StreamTokenizer.TT_WORD) {
            if ((this.st.sval.equals("vars") || this.st.sval.equals("quit")) || this.st.sval.equals("clear")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the current token is "protected".
     * @return  true in case of a protected word, false otherwise
     */
    public boolean isKeyword() {
        return (isCommand() || isUnary() || this.st.sval.equals("ans"));
    }

    /**
     * Checks whether the current token is an operator.
     * @return  true in case of an operator, false otherwise
     */
    public boolean isOperator() {
        switch (this.st.ttype) {
            case '+': 
                return true;
            case '-':
                return true;
            case '*': 
                return true;
            case '/':
                return true;
            case '=':
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns an instance for a given command.
     * @return  a symbolic expression according to the Abstract Syntax Tree (AST)
     * @throws IOException  if the underlying StreamTokenizer does not catch a more specific error
     */
    public SymbolicExpression command() throws IOException {
        if (this.st.sval.equals("vars")) {
            return Vars.instance();
        } else if (this.st.sval.equals("quit")) {
            return Quit.instance();
        } else {
            return Clear.instance();
        }
    }

    /**
     * Checks whether the current token is a unary operation.
     * @return  true in case of a unary operation, false otherwise
     */ 
    public boolean isUnary() {
        if (this.st.sval.equals("exp")) {
            return true;
        } else if (this.st.sval.equals("log")) {
            return true;
        } else if (this.st.sval.equals("sin")) {
            return true;
        } else if (this.st.sval.equals("cos")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the current token (word) is a named constant.
     * @return  true in case of a named constant, false otherwise
     */
    public boolean isNamedConstant() {
        if (Constants.namedConstants.containsKey(this.st.sval)) {
            return true;
        }
        return false;
    }

    /**
     * Creates a new StreamTokenizer, feeds an input string sets up parsing rules.
     * @param inputString  string to be parsed
     */
    public void parse(String inputString) {
        this.st = new StreamTokenizer(new StringReader(inputString));
        this.st.ordinaryChar('-'); /// parse object-oriented as "object" - "oriented" :) 
        this.st.ordinaryChar('/');
        this.st.eolIsSignificant(true);
    }

}