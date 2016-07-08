package com.jooyunghan;

import org.jcsp.lang.*;

public class JCspMain {
    public static void main(String[] args) {
        int n = 100;
        int m = 100;

        One2OneChannel channels[] = new One2OneChannel[n];
        for (int i = 0; i < channels.length; i++)
            channels[i] = Channel.one2one();

        CSProcess processes[] = new CSProcess[n + 1];
        processes[0] = () -> {
            channels[0].out().write(1);
            channels[0].out().write(-1);
        };

        for (int i = 1; i < processes.length - 1; i++) {
            processes[i] = next(channels[i - 1].in(), channels[i].out());
        }

        processes[n] = () -> {
            for (int i = 0; i < m - 1; i++) {
                System.out.print((int) channels[n - 1].in().read());
            }
        };

        new Parallel(processes).run();
        // Parallel도 역시 Native thread를 사용한다
        /*
Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:714)
	at org.jcsp.lang.Parallel.run(Unknown Source)
	at com.jooyunghan.JCspMain.main(JCspMain.java:31)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)
         */
    }

    private static CSProcess next(AltingChannelInput in, ChannelOutput out) {
        return () -> {
            int prev = -1;
            int count = 0;
            while (true) {
                int n = (int) in.read();
                if (n == -1) {
                    break;
                } else if (prev == -1) {
                    prev = n;
                    count = 1;
                } else if (prev == n) {
                    count++;
                } else {
                    out.write(count);
                    out.write(prev);
                    prev = n;
                    count = 1;
                }
            }
            out.write(count);
            out.write(prev);
            out.write(-1);
        };
    }


}
