package com.jooyunghan.csp;

interface GoRoutine {
    static void go(GoRoutine f) {
        Thread t = new Thread(() -> {
            try {
                f.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    void run() throws Exception;
}
