package com.owneroftime.app;

import java.util.concurrent.CompletableFuture;

public interface AppService {
    CompletableFuture<Long> computeFactorial(long number);
}
