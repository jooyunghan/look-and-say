package com.jooyunghan;


import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.SynchronousQueue;

public class GeneratorMain {
    interface GeneratorFunc<T> {
        void run(YieldFunc<T> g);
    }

    interface YieldFunc<T> {
        void yield(T t);
    }

    static class Generator<T> implements Iterable<T> {
        SynchronousQueue<Optional<T>> queue = new SynchronousQueue<>();

        private Generator() {
        }

        public Optional<T> next() {
            try {
                return queue.take();
            } catch (InterruptedException e) {
                return Optional.empty();
            }
        }

        private void yield(T t) {
            try {
                queue.put(Optional.of(t));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void close() {
            try {
                queue.put(Optional.empty());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static <T> Generator<T> of(GeneratorFunc<T> f) {
            Generator<T> g = new Generator<>();
            new Thread(() -> {
                f.run(g::yield);
                g.close();
            }).start();
            return g;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                Optional<T> next = Generator.this.next();

                @Override
                public boolean hasNext() {
                    return next.isPresent();
                }

                @Override
                public T next() {
                    T value = next.get();
                    next = Generator.this.next();
                    return value;
                }
            };
        }
    }

    public static void main(String[] args) {
        Generator<Integer> ints = Generator.of((g) -> {
            int i = 0;
            while (i < 100)
                g.yield(i++);
        });

        for (int i : ints) {
            System.out.println(i);
        }

        Iterable<Integer> s = Arrays.asList(1);
        for (int i=0; i < 100; i++) {
            s = next(s);
        }

        Iterator<Integer> ant = s.iterator();
        for (int i=0; i<100; i++) {
            System.out.println(ant.next());
        }
    }

    private static Iterable<Integer> next(Iterable<Integer> s) {
        return Generator.<Integer>of((g) -> {
            Iterator<Integer> i = s.iterator();
            int prev = i.next();
            int count = 1;
            while (i.hasNext()) {
                int c = i.next();
                if (prev == c)
                    count++;
                else {
                    g.yield(count);
                    g.yield(prev);
                    prev = c;
                    count = 1;
                }
            }
            g.yield(count);
            g.yield(prev);
        });
    }


}
