# Butterp

A simple lisp implementation in Java.

## Build & Run

This project uses maven, so to build it you can do:

    mvn package
    
This creates a jar file in the target directory. To run it, you do:

    java -jar target/butter0-1.0-SNAPSHOT.jar
    
To exit the REPL type `:exit` at the prompt.

## List of Primitives

### Atoms

* t
* nil
* integers
* symbols

### Special forms

* (define symbol sexpr)
* (quote sexpr)
* (lambda (args) body)
* (if cond-sexpr then-sexpr else-sexpr)
* (macro (arg) body)

### Primitive functions

* (car list)
* (cdr list)
* (cons sexpr list)
* (eq sexpr sexpr)
* (add integer integer)
* (mult integer integer)
* (eval sexpr)
* (apply fn-sexpr args-list)
