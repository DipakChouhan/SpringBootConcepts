package com.owneroftime.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AppConfig implements AsyncConfigurer {

    private static final int CORE_POOL_SIZE = 5;
    private static final int QUEUE_CAPACITY= 5;
    private static final String THREAD_NAME_PREFIX = "App_Thread_";


    /**
     * This method will configure the Executer that is responsible for
     * executing @Async annotated methods asynchronously for each call
     * @return Executor
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        threadPoolTaskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        threadPoolTaskExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
