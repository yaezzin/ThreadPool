package com.example.concurrency.basic;


public class SimpleThreadTest {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println(Thread.currentThread().getName());
    }
}
