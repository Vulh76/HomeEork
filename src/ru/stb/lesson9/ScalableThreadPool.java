package ru.stb.lesson9;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ScalableThreadPool implements ThreadPool {
    private final int minThreadCount;
    private final int maxThreadCount;
    private final List<Thread> threads;
    private final Queue<Runnable> tasks;
    private final Worker worker;

    public ScalableThreadPool(int minThreadCount, int maxThreadCount) {
        this.minThreadCount = minThreadCount;
        this.maxThreadCount = maxThreadCount;
        worker = new Worker();
        tasks = new ArrayDeque<>();
        threads = new ArrayList<>(minThreadCount);
        for (int i = 0; i < minThreadCount; i++) {
            threads.add(new Thread(worker));
        }
    }

    @Override
    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        tasks.add(runnable);
    }

    class Worker implements Runnable {
        @Override
        public void run() {

        }
    }
}
