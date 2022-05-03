package com.william.example.outofmemory;


/**
 *  VM Args: -Xss2m
 *
 *  内存溢出 java.lang.OutOfMemoryError:
 *          unable to create new native thread
 */
public class VMNativeStack {

    private void dontStop() {
        while (true) {

        }
    }

    public void stackLeakByThead() {
        while (true) {
            new Thread(new Runnable() {
                public void run() {
                    dontStop();
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        VMNativeStack oom = new VMNativeStack();
        oom.stackLeakByThead();
    }
}
