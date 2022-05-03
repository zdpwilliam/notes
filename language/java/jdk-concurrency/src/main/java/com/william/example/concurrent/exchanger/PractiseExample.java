package com.william.example.concurrent.exchanger;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Exchanger;

/**
 * Created by william on 2016/11/28.
 */
public class PractiseExample {

    public static void main(String[] args) {
        new FillAndEmpty().start();
    }

    static class FillAndEmpty {
        private static final int DEFAULT_COUNT = 5;

        class FillingLoop implements Runnable {
        	private Exchanger<Queue<Integer>> exchanger;
        	
            Queue<Integer> initialEmptyBuffer = new ArrayBlockingQueue<Integer>(DEFAULT_COUNT);
        	
            public FillingLoop(Exchanger<Queue<Integer>> exchanger) {
				super();
				this.exchanger = exchanger;
			}

			public void run() {
                Queue<Integer> currentBuffer = initialEmptyBuffer;
                try {
                    while (true) {
                        addToBuffer(currentBuffer);
                        if (isFull(currentBuffer))
                            currentBuffer = exchanger.exchange(currentBuffer);
                        
                        System.out.println("-------------------------------- FillingLoop exchange" + currentBuffer);
                        Thread.currentThread().sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    System.out.println("FillingLoop" + ex);
                }
            }
        }

        class EmptyingLoop implements Runnable {
        	private Exchanger<Queue<Integer>> exchanger;
        	
        	Queue<Integer> initialFullBuffer =  new ArrayBlockingQueue<Integer>(DEFAULT_COUNT);
            {
                for (int i = 0; i < DEFAULT_COUNT; i++) {
                    initialFullBuffer.offer(i);
                }
            }
            
            public EmptyingLoop(Exchanger<Queue<Integer>> exchanger) {
				super();
				this.exchanger = exchanger;
			}

			public void run() {
                Queue<Integer> currentBuffer = initialFullBuffer;
                try {
                    while (true) {
                        takeFromBuffer(currentBuffer);
                        if (currentBuffer.isEmpty())
                            currentBuffer = exchanger.exchange(currentBuffer);
                        
                        System.out.println("-------------------------------- EmptyingLoop exchange" + currentBuffer);
                        Thread.currentThread().sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    System.out.println("EmptyingLoop" + ex);
                }
            }
        }

        void start() {
        	Exchanger<Queue<Integer>> exchanger = new Exchanger<Queue<Integer>>();
            new Thread(new FillingLoop(exchanger)).start();
            new Thread(new EmptyingLoop(exchanger)).start();
        }

        private void addToBuffer(Queue<Integer> currentBuffer) {
            Random random = new Random(System.nanoTime());
            int randomInt = random.nextInt(100);
            System.out.println("before Add ----- Buffers: " + currentBuffer);
            System.out.println("Buffer Add Content: " + randomInt);
            currentBuffer.offer(randomInt);
            System.out.println("after  Add ----- Buffers: " + currentBuffer);
        }

        private boolean isFull(Queue<Integer> currentBuffer) {
            return currentBuffer.size() == DEFAULT_COUNT;
        }

        private void takeFromBuffer(Queue<Integer> currentBuffer) {
            if (!currentBuffer.isEmpty()) {
            	System.out.println("before ----- Buffers: " + currentBuffer);
                System.out.println("Buffer Take content : " + currentBuffer.poll());
                System.out.println("after  ----- Buffers: " + currentBuffer);
            }
        }
    }
}
