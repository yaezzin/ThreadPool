package com.example.concurrency.executorservice;

import java.util.concurrent.*;

public class ScheduledExecutorServiceExample {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // 5초 후에 작업을 실행
        scheduler.schedule(() -> System.out.println("5 seconds have passed!"), 5, TimeUnit.SECONDS);

        // 1초 후에 시작하고, 그 이후에는 1초 간격으로 반복 실행
        scheduler.scheduleAtFixedRate(() -> System.out.println("Repeated task executed"), 1, 1, TimeUnit.SECONDS);

        // 1초 후에 시작하고, 각 작업이 끝난 후 2초의 간격을 두고 실행
        scheduler.scheduleWithFixedDelay(() -> System.out.println("Task with delay executed"), 1, 2, TimeUnit.SECONDS);
    }
}
