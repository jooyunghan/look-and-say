package com.jooyunghan.lookandsay;

import java.util.Arrays;
import java.util.Iterator;

interface Ant extends Iterator<Integer> {
    default void remove() {
        throw new UnsupportedOperationException();
    }
}

class Init implements Ant {
    private Iterator<Integer> it = Arrays.asList(1).iterator();

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public Integer next() {
        return it.next();
    }
}

class Next implements Ant {

    enum State {INIT, HAS_NEXT, COUNT, LAST}

    private Iterator<Integer> inner;
    private State state;
    private int elem;
    private int count;
    private int next;

    public Next(Iterator<Integer> inner) {
        this.inner = inner;
        this.state = State.INIT;
    }

    @Override
    public boolean hasNext() {
        return state != State.INIT || inner.hasNext();
    }

    @Override
    public Integer next() {
        if (state == State.INIT) {
            state = State.HAS_NEXT;
            next = inner.next();
        }

        if (state == State.HAS_NEXT) {
            state = State.LAST;
            elem = next;
            count = 1;
            while (inner.hasNext()) {
                int next = inner.next();
                if (next == elem) {
                    count++;
                } else {
                    state = State.COUNT;
                    this.next = next;
                    break;
                }
            }
            return count;
        } else if (state == State.LAST) {
            state = State.INIT;
            return elem;
        } else {
            state = State.HAS_NEXT;
            return elem;
        }
    }
}

public class IteratorAnt {

    public static void main(String[] args) {
        Ant ant = new Init();

        for (int i = 0; i < 4948; i++)
            ant = new Next(ant);

        for (int i = 0; i < 4948; i++)
            ant.next();

        System.out.print(ant.next());
    }
}

/*

n == 4948에서 StackOverflowError

Exception in thread "main" java.lang.StackOverflowError
	at com.jooyunghan.lookandsay.Next.next(IteratorAnt.java:26)
	at com.jooyunghan.lookandsay.Next.next(IteratorAnt.java:50)
	at com.jooyunghan.lookandsay.Next.next(IteratorAnt.java:26)
	at com.jooyunghan.lookandsay.Next.next(IteratorAnt.java:50)
	at com.jooyunghan.lookandsay.Next.next(IteratorAnt.java:26)
	at com.jooyunghan.lookandsay.Next.next(IteratorAnt.java:50)
	at com.jooyunghan.lookandsay.Next.next(IteratorAnt.java:26)
	at com.jooyunghan.lookandsay.Next.next(IteratorAnt.java:50)
 */