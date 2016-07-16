package com.jooyunghan.lookandsay;

import java.util.Iterator;

public class Next implements Iterator<Integer> {

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
