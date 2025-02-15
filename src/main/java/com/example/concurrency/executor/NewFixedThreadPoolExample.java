package com.example.concurrency.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewFixedThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            Runnable task = () -> {
                System.out.println("Task executed by: " + Thread.currentThread().getName());
            };
            executorService.execute(task);
        }
        executorService.shutdown();
    }
}
