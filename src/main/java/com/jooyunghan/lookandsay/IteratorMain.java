package com.jooyunghan.lookandsay;

import java.util.Iterator;

import static com.jooyunghan.java8.Lists.of;

public class IteratorMain {

    public static void main(String[] args) {
        Iterator<Integer> ant = of(1).iterator();

        for (int i = 0; i < 100; i++)
            ant = new Next(ant);

        for (int i = 0; i< 100 ; i++)
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