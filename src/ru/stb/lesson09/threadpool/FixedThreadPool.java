package ru.stb.lesson09.threadpool;

import java.util.ArrayDeque;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {
    private final Thread[] threads;
    private final Queue<Runnable> tasks;
    private volatile boolean isRunning = true;

    public FixedThreadPool(int threadCount) {
        tasks = new ArrayDeque<>();
        threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new Worker());
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
        if(isRunning) {
            synchronized (tasks) {
                tasks.add(runnable);
                tasks.notifyAll();
            }
        }
    }

    @Override
    public void shutdown() {
        isRunning = false;
    }

    private final class Worker implements Runnable {
        @Override
        public void run() {
            while (isRunning && !Thread.currentThread().isInterrupted()) {
                Runnable task;
                synchronized (tasks) {
                    try {
                        while(tasks.size() == 0) {
                            tasks.wait();
                        }
                    } catch (InterruptedException e) {
                        break;
                    }
                    task = tasks.poll();
                }
                task.run();
            }
        }
    }
}
