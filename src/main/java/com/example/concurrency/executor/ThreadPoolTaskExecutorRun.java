package com.example.concurrency.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@DependsOn("threadPoolTaskExecutor")
public class ThreadPoolTaskExecutorRun {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Async(value = "threadPoolTaskExecutor")
    public void start() throws InterruptedException {
        int poolSize = threadPoolTaskExecutor.getPoolSize();
        int activeCount = threadPoolTaskExecutor.getActiveCount();
        int remainingCapacity = threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().remainingCapacity();
        int queueSize = threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size();

        log.info("Thread: {}, poolSize: {} activeCount: {}, waitingQueue: {}, queueCapacity: {}",
                Thread.currentThread().getName(), poolSize, activeCount, queueSize, remainingCapacity);

        Thread.sleep(10000);
    }
}
