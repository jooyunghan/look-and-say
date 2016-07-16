package com.jooyunghan.csp;


import static com.jooyunghan.csp.Chan.chan;
import static com.jooyunghan.csp.Go.go;

public class CspMain {
    public static void main(String[] args) throws InterruptedException {
        Chan c = init();

        int n = 2025; // maximum value of n,m = 2025
        int m = 2025;

        while (n-- > 0) {
            c = next(c);
        }

        while (m-- > 0) {
            System.out.print(c.recv());
        }

        // TODO terminate all goroutines gracefully
    }

    private static Chan init() {
        Chan c = chan();
        go(() -> {
            c.send(1);
            c.close();
        });
        return c;
    }

    private static Chan next(Chan in) {
        Chan out = chan();
        go(() -> {
            int prev = -1;
            int count = 0;
            while (true) {
                int n = in.recv();
                if (n == -1) { // closed
                    break;
                } else if (prev == -1) { // first
                    prev = n;
                    count = 1;
                } else if (n == prev) {
                    count++;
                } else {
                    out.send(count);
                    out.send(prev);
                    prev = n;
                    count = 1;
                }
            }
            out.send(count);
            out.send(prev);
            out.close();
        });
        return out;
    }
/*
Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:714)
	at com.jooyunghan.csp.GoRoutine.go(GoRoutine.java:12)
	at com.jooyunghan.csp.CspMain.next(CspMain.java:36)
	at com.jooyunghan.csp.CspMain.main(CspMain.java:15)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)
 */

}
