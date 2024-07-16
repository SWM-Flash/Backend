package com.first.flash.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {

    @Value("${scheduler.pool.size}")
    private int poolSize;

    @Value("${scheduler.threadNamePrefix}")
    private String threadNamePrefix;

    @Value("${scheduler.awaitTerminationSeconds}")
    private int awaitTerminationSeconds;

    @Value("${scheduler.waitForTasksToCompleteOnShutdown}")
    private boolean waitForTasksToCompleteOnShutdown;

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(poolSize);
        scheduler.setThreadNamePrefix(threadNamePrefix);
        scheduler.setAwaitTerminationSeconds(awaitTerminationSeconds);
        scheduler.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
        return scheduler;
    }
}
