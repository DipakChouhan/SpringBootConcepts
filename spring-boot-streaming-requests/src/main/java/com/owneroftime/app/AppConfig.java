package com.owneroftime.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // Enable asynchronous behavior
public class AppConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Defines minimum number of threads that can run parallel
        executor.setMaxPoolSize(5); // Max number of threads that can run in parallel
        executor.setThreadNamePrefix("OFTExecuter_"); // Thread name prefix
        executor.initialize();
        return executor;
    }
}
