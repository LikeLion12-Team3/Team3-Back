package com.likelion.byuldajul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); // 코어 스레드 풀의 크기를 2로 설정
        executor.setMaxPoolSize(2); // 최대 스레드 풀의 크기를 2로 설정
        executor.setQueueCapacity(100); // 작업 큐의 용량을 100으로 설정
        executor.setThreadNamePrefix("SummaryUpdateThread-");
        executor.initialize(); // 스레드 풀 초기화
        return executor;
    }
}
