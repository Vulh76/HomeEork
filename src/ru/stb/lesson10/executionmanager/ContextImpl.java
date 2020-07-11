package ru.stb.lesson10.executionmanager;

import ru.stb.lesson10.executionmanager.api.Context;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ContextImpl implements Context {
    private final List<Future<?>> futures;
    private final Runnable callback;
    private volatile boolean isFinished = false;
    private final AtomicInteger failedTaskCount = new AtomicInteger(0);
    private final AtomicInteger completedTaskCount = new AtomicInteger(0);
    private final AtomicInteger interruptedTaskCount = new AtomicInteger(0);

    public ContextImpl(Runnable callback, List<Future<?>> futures) {
        this.futures = futures;
        this.callback = callback;

        //futures.get(5).cancel(true);

        getAll();
    }

    @Override
    public int getCompletedTaskCount() {
        //return (int) futures.stream().filter(Future::isDone).count();
        return completedTaskCount.get();
    }

    @Override
    public int getFailedTaskCount() {
        return failedTaskCount.get();
    }

    @Override
    public int getInterruptedTaskCount() {
        //return (int) futures.stream().filter(Future::isCancelled).count();
        return interruptedTaskCount.get();
    }

    @Override
    public void interrupt() {
        for (Future<?> future : futures) {
            future.cancel(false);
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    private void getAll() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        try {
            threadPool.execute(new Runner());
        } catch (Exception e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }

    private final class Runner implements Runnable {
        @Override
        public void run() {
            for (Future<?> future : futures) {
                try {
                    future.get();
                    completedTaskCount.getAndIncrement();
                } catch (InterruptedException ignore) {
                } catch (CancellationException e) {
                    interruptedTaskCount.getAndIncrement();
                } catch (Exception e) {
                    failedTaskCount.getAndIncrement();
                }
            }
            isFinished = true;
            callback.run();
        }
    }
}
