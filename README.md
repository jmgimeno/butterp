# Butterp

A simple lisp implementation in Java.

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
