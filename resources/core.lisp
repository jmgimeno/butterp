(define inc
    (lambda (n)
        (add n 1)))

(define dec
    (lambda (n)
        (add n -1)))

(define cadr
    (lambda (l)
        (car (cdr l))))

(define cddr
    (lambda (l)
        (cdr (cdr l))))


(define caddr
    (lambda (l)
        (car (cdr (cdr l)))))

(define not
    (lambda (b)
        (if b nil t)))

(define and
    (macro (expr)
        (if (eq expr nil)
            t
            (list 'if (car expr)
                      (cons 'and (cdr expr))
                      nil))))

(define or
    (macro (expr)
        (if (eq expr nil)
            nil
            (list 'if (car expr)
                      t
                      (cons 'or (cdr expr))))))

(define mapper
    (lambda (m)
        (lambda (f l)
            (if (eq l nil)
                nil
                (m (f (car l))
                        ((mapper m) f (cdr l)))))))

(define map (mapper cons))

(define filter
    (lambda (f l)
        (if (eq l nil)
            nil
            (if (f (car l))
                (cons (car l)
                      (filter f (cdr l)))
                (filter f (cdr l))))))

(define snoc
    (lambda (l x)
        (cons x l)))

(define reduce
    (lambda (f init l)
        (if (eq l nil)
            init
            (reduce f
                    (f init (car l))
                    (cdr l)))))

(define reverse
    (lambda (l)
        (reduce snoc nil l)))

(define append
    (lambda (l1 l2)
        (if (eq l1 nil)
            l2
            (cons (car l1)
                  (append (cdr l1) l2)))))

(define mappend (mapper append))
