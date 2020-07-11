package ru.stb.lesson10.executionmanager;

import ru.stb.lesson10.executionmanager.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutionManagerImpl implements ExecutionManager {

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<?>> futures = new ArrayList<>();
        for (Runnable task : tasks) {
            futures.add(threadPool.submit(task));
        }

        threadPool.shutdown();
        return new ContextImpl(callback, futures);

        /*CompletableFuture<?>[] futures = new CompletableFuture<?>[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            futures[i] = CompletableFuture.runAsync(tasks[i]);
        }
        CompletableFuture<?> future = CompletableFuture.completedFuture(futures);*/
    }
}
