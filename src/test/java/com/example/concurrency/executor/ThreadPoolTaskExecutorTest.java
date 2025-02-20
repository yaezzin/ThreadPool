package com.example.concurrency.executor;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadPoolTaskExecutorTest {

    @Test
    public void ThreadPoolTaskExecutorInitTest() {
        ThreadPoolTaskExecutor executor = makeThreadPoolTaskExecutor();
        assertEquals(0, executor.getThreadPoolExecutor().getPoolSize(), "Initial pool size should be 0.");
    }

    @Test
    public void ThreadPoolTaskExecutorPrestartTest() {
        ThreadPoolTaskExecutor executor = makeThreadPoolTaskExecutor();
        executor.setPrestartAllCoreThreads(true);
        assertEquals(executor.getPoolSize(), executor.getThreadPoolExecutor().getPoolSize(), "set Prestart All Core Threads");
    }

    private ThreadPoolTaskExecutor makeThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(10);
        executor.initialize();

        return executor;
    }
}
