package com.jooyunghan.csp;

public class Go {
    interface GoRoutine {
        void run() throws Exception;
    }
    static public void go(GoRoutine f) {
        new Thread(() -> {
            try {
                f.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
