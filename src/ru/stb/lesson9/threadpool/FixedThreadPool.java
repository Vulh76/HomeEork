package ru.stb.lesson9.threadpool;

import java.util.ArrayDeque;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {
    private final int threadCount;
    private final Thread[] threads;
    private final Queue<Runnable> tasks;

    public FixedThreadPool(int threadCount) {
        this.threadCount = threadCount;
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
        synchronized (tasks) {
            tasks.add(runnable);
            tasks.notifyAll();
        }
    }

    @Override
    public void stop() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    class Worker implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                Runnable task;
                synchronized (tasks) {
                    while(tasks.size() == 0) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException ignore) { }
                    }
                    if(Thread.currentThread().isInterrupted())
                        break;
                    task = tasks.poll();
                }
                task.run();
            }
        }
    }
}
