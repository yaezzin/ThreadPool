package com.example.concurrency.basic;

public class SameThread {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };

        runnable.run();
        System.out.println(Thread.currentThread().getName());
    }
}
