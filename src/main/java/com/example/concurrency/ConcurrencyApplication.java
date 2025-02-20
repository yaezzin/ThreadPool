package com.example.concurrency;

import com.example.concurrency.executor.ThreadPoolTaskExecutorRun;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConcurrencyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConcurrencyApplication.class, args);

        ThreadPoolTaskExecutorRun run = context.getBean(ThreadPoolTaskExecutorRun.class);
        for (int i = 0; i < 100; i++) {
            try {
                run.start(); // 비동기 작업 호출
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
