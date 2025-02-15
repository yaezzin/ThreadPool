package com.example.concurrency.executorservice;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutorServiceTest {
    @Test
    void shutdown() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName());
        executorService.execute(runnable);

        executorService.shutdown();

        assertThrows(RejectedExecutionException.class, () -> executorService.execute(runnable));
    }

    @Test
    void shutdownNow() throws InterruptedException {
        Runnable task = () -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted");
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(task);

        executorService.shutdownNow();
        Thread.sleep(1000);
    }

    @Test
    void awaitTermination() throws InterruptedException {
        Runnable task = () -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted");
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(task);

        executorService.shutdown();

        boolean terminated = executorService.awaitTermination(5, TimeUnit.SECONDS);
        assertTrue(terminated);
    }

    @Test
    void awaitTerminationTimeout() throws InterruptedException {
        Runnable task = () -> {
            try {
                // Executes the task after waiting for 2 seconds
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted");
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(task);

        executorService.shutdown();

        // Waits for termination for 1 second (a timeout will occur, and it will return false).
        boolean terminated = executorService.awaitTermination(1, TimeUnit.SECONDS);
        assertFalse(terminated);
    }
}

