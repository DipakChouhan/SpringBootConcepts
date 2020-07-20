package com.owneroftime.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AppServiceImpl implements AppService{

    private final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    /**
     * This method will run asynchronously for each call in separate Thread.
     * It Must return a CompletableFuture to work.
     * @param number
     * @return
     */
    @Async
    @Override
    public CompletableFuture<Long> computeFactorial(long number) {
        logger.info("Computing Factorial For: {}" , number);
        long result = number;
        for (long i=  number-1; i >= 1; i-- ) {
            result = result * i;
            try {
                Thread.sleep(i/2); // Intentional Delay
            } catch (Exception e) {
            }
        }
        logger.info("Computed Factorial For: {}" , number);
        return CompletableFuture.completedFuture(result);
    }
}
