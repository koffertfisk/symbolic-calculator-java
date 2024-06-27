# Symbolic Calculator Java
Symbolic calculator developed in Java that parses expressions symbol-by-symbol and evaluates results according to an Absract Syntax Tree (AST). 

## Dependencies
- `javac` Java compiler and `java` runtime (OpenJDK for example)

## Building The Application
To compile and run:
-  `make` to build
-  `make test` to run complete test suite
-  `make asttest` to run AST test suite
-  `make parsertest` to run symbolic parser test suite
-  `make parser` to run a minimal program which exits after executing a single statement
-  `make run` to build and run the calculator
-  `make doc`to build javadoc documentation
-  `make clean` to remove compiled output files and directory

## Usage
Starting the application is done by executing `make run`. Statements can be entered after the user input sign `?`. Expressions are evaluated from left to right.

### Commands
- `vars` for displaying stored variables
- `clear`for removing all stored variables
- `quit` to exit the application

### Mathematical Features
- addition (`+`) and subtraction (`-`)  
- multiplication (`*`) and division (`/`)
- `sin`, `cos`, `exp`and `log` functions
- negative numbers (`-`)
- variable assignment (`1=x`)
- order of operation using parenthesis syntax, e.g. `(x+3)*y`
- `pi`, `e`, `L` and `Answer` as named constants
- previous evaluation using the keyword `ans`

### Example Execution
An example session is shown below. Comments inside brackets [ and ] are for demonstration purposes only and, hence, does not show during execution of the actual application.

    Welcome to the Symbolic Parser!
    ? 1+2	[addition]
    3.0
    ? 5=x	[variable assignment]
    5.0
    ? -x	[negation support]
    -5.0
    ? ans	[output last evaluated answer]
    -5.0
    sin(0.5)	[unary operations cos, sin, exp, log are supported]
    0.479425538604203
    ? x=y=z		[multiple assignment]
    5.0
    ? vars		[printing all variables]
    ans = 5.0
    x = 5.0
    y = 5.0
    z = 5.0
    ? (x+y)*3	[parenthesis support]
    30.0
    ? clear		[clearing all variables]
    ? vars
    ? 1+a
    1.0 + a		[non-fully reduced expression]
    ? w+y
    w + y
    ? vars
    ans = w + y
    ? sin(exp(x))	[nested unary functions]
    sin(exp(x))
    ? 5=x
    5.0
    ? sin(exp(x))
    -0.6876914116945115
    ? c*d=f
    c * d
    ? -4=d
    -4.0
    ? f
    c * d		[expressions are evaluated once]
    ? 1=pi
	*** Error: cannot redefine named constant
	
	Try again.
	?
	? 3**x
	*** Syntax Error: unexpected operator
	
	Try again.
	? quit
	
    Exiting application

    Number of expressions entered: 16

    Number of successful evaluations: 15

    Number of fully reduced evaluations: 12

    Bye!

## Tests    
Tests can be performed by executing `make test` (after building with `make`) which will feed statements to the symbolic parser and test printing of the intercepted expression and their evaluated results. `make asttest`bypasses the parser and tests the symbolic tree whereas `make parsertest` will test both parts. 