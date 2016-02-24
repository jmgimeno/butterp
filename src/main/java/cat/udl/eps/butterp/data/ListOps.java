package cat.udl.eps.butterp.data;

import java.util.*;

public class ListOps {

    public static SExpression cons(SExpression car, SExpression cdr) {
        return new ConsCell(car, cdr);
    }

    public static SExpression car(SExpression cell) {
        return ((ConsCell) cell).car;
    }

    public static SExpression cdr(SExpression cell) {
        return ((ConsCell) cell).cdr;
    }

    public static SExpression list(SExpression... elems) {
        return list(Arrays.asList(elems));
    }

    public static SExpression list(List<SExpression> elems) {
        SExpression list = Symbol.NIL;
        for (int i = elems.size(); i > 0; i--) {
            list = cons(elems.get(i - 1), list);
        }
        return list;
    }

    public static int length(SExpression list) {
        int length = 0;
        for (SExpression elem : iter(list)) {
            length += 1;
        }
        return length;
    }

    public static SExpression nth(SExpression list, int n) {
        for (SExpression elem: iter(list)) {
            if (n == 0) return elem;
            n -= 1;
        }
        throw new IllegalArgumentException(); // Should never happen
    }

    public static boolean isListOf(SExpression sexpr, Class<?> klass) {
        try {
            for (SExpression elem: iter(sexpr)) {
                if (!klass.isInstance(elem))
                    return false;
            }
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }

    public static boolean allDifferent(SExpression list) {
        Set<SExpression> seen = new HashSet<>();
        for (SExpression elem: iter(list)) {
            if (!seen.add(elem))
                return false;
        }
        return true;
    }

    private static Iterator<SExpression> NIL_ITERATOR = new Iterator<SExpression> () {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public SExpression next() {
            throw new NoSuchElementException();
        }
    };



    public static Iterable<SExpression> iter(SExpression list) {
        return new Iterable<SExpression>() {

            class ListIterator implements Iterator<SExpression> {
                private SExpression current;

                public ListIterator() {
                    this.current = list;
                }

                @Override
                public boolean hasNext() {
                    return current != Symbol.NIL;
                }

                @Override
                public SExpression next() {
                    if (!hasNext())
                        throw new NoSuchElementException();
                    SExpression result = car(current);
                    current = cdr(current);
                    return result;
                }
            }

            @Override
            public Iterator<SExpression> iterator() {
                return new ListIterator();
            }
        };
    }
}
