package com.example.concurrency.executorservice;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

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
                System.out.println(Thread.currentThread().getName());
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
                System.out.println(Thread.currentThread().getName());
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
                System.out.println(Thread.currentThread().getName());
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(task);

        executorService.shutdown();

        // Waits for termination for 1 second (a timeout will occur, and it will return false).
        boolean terminated = executorService.awaitTermination(1, TimeUnit.SECONDS);
        assertFalse(terminated);
    }

    @Test
    void submit() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Runnable runnable = () -> {
            String message = "Hello from Runnable task";
            System.out.println(message);
        };

        Future<?> future = executorService.submit(runnable); // submit(Runnable task), submit(Runnable task, T result)
        assertNull(future.get());
    }

    @Test
    void invokeAll() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<String>> callable = Arrays.asList(
                () -> {
                    Thread.sleep(100);
                    String result = "Task1";
                    return result;
                },

                () -> {
                    Thread.sleep(100);
                    String result = "Task2";
                    return result;
                },

                () -> {
                    Thread.sleep(100);
                    String result = "Task3";
                    return result;
                });

        List<Future<String>> results = executorService.invokeAll(callable);

        for (Future<String> result : results) {
            System.out.println(result.get());
        }

        executorService.shutdown();
    }
}

