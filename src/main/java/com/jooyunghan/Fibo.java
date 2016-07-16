package com.jooyunghan;

import java.util.Iterator;

/**
 * Created by jooyung.han on 7/16/16.
 */
public class Fibo implements Iterator<Integer> {
    int a = 0;
    int b = 1;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Integer next() {
        int next = a;
        b = a + b;
        a = b - a;
        return next;
    }

    public static void main(String[] args) {
        Iterator<Integer> fibo = new Fibo();
        for (int i=0; i<10; i++) {
            System.out.println(fibo.next());
        }
    }
}
