package com.jooyunghan.java8;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by jooyung.han on 7/16/16.
 */
public class StreamMain {
    public static void main(String[] args) {
        ant().skip(100).findFirst().get().limit(100).forEach(c -> System.out.print(c));
    }

    static Stream<Stream<Integer>> ant() {
        return Stream.iterate(Stream.of(1), StreamMain::next);
    }

    private static Stream<Integer> next(Stream<Integer> t) {
        return runLength(t).flatMap(run -> Stream.of(run.count, run.elem));
    }

    static class RunLength<A> {
        A elem;
        int count;
        public RunLength(A elem, int count) {
            this.elem = elem;
            this.count = count;
        }
    }

    private static <A> Stream<RunLength<A>> runLength(Stream<A> t) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                runLength(t.iterator()),
                Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
    }

    private static <A> Iterator<RunLength<A>> runLength(final Iterator<A> inner) {
        return new Iterator<RunLength<A>>() {
                A elem;
                @Override
                public boolean hasNext() {
                    return inner.hasNext() || elem != null;
                }

                @Override
                public RunLength<A> next() {
                    A old;
                    if (elem == null) {
                        old = inner.next();
                    } else {
                        old = elem;
                        elem = null;
                    }
                    int count = 1;
                    while (inner.hasNext()) {
                        elem = inner.next();
                        if (old.equals(elem)) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    return new RunLength<>(old, count);
                }
            };
    }


}
