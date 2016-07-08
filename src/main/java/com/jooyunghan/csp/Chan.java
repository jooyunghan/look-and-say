package com.jooyunghan.csp;

import java.util.concurrent.SynchronousQueue;

public class Chan {
    SynchronousQueue<Integer> chan = new SynchronousQueue<>();

    public static Chan chan() {
        return new Chan();
    }

    public void send(int n) throws InterruptedException {
        chan.put(n);
    }

    public void close() throws InterruptedException {
        chan.put(-1);
    }

    public int recv() throws InterruptedException {
        return chan.take();
    }
}
