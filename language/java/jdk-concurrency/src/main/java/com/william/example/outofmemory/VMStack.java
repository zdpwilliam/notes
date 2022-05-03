package com.william.example.outofmemory;


/**
 *  VM Args: -Xss128k
 */
public class VMStack {

    private int stackLenght = 1;

    public void stackLeak() {
        stackLenght ++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        VMStack oom = new VMStack();

        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println(" stack length: " + oom.stackLenght);
            throw e;
        }
    }
}
