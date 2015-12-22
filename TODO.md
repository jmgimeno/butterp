## Known bugs

* or & and do not return the last truish evaluated expression

## Design

* define interfaces Evaluable and Applicable to "simplify" hierarchy?
* define an interface EvaluationStrategy to evaluate (or not) the arguments
before applying. It has to be linked to function or special.

## Tests

* Add tests for core.lisp functionality

## Functionality

* ; as comment till the end-of-line
* let and let* in core.lisp
* gensym to avoid capturing symbols in macros
* macroexpand-1
* quasi-quote & unquote
* function with last argument for rest of parameters
* macros with more than one arg
* add strings as atoms
* load files from filesystem
