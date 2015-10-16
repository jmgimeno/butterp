(define inc
    (lambda (n)
        (add n 1)))

(define dec
    (lambda (n)
        (add n -1)))

(define cadr
    (lambda (l)
        (car (cdr l))))

(define caddr
    (lambda (l)
        (car (cdr (cdr l)))))

