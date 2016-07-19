package com.jooyunghan;


import java.util.Optional;
import java.util.concurrent.SynchronousQueue;

public class GeneratorMain {
    interface GeneratorFunc<T> {
        void run(Generator<T> g);
    }
    static class Generator<T> {
        SynchronousQueue<Optional<T>> queue = new SynchronousQueue<>();

        public Optional<T> next() {
            try {
                return queue.take();
            } catch (InterruptedException e) {
                return Optional.empty();
            }
        }

        public void yield(T t) {
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
                f.run(g);
                g.close();
            }).start();
            return g;
        }
    }

    public static void main(String[] args) {
        Generator<Integer> ints = Generator.of((g) -> {
            int i = 0;
            while (i<100)
                g.yield(i++);
        });

        while (true) {
            Optional<Integer> next = ints.next();
            if (!next.isPresent())
                break;
            System.out.println(next.get());
        }
    }

}
