package ru.stb.lesson9.threadpool;

import java.util.ArrayDeque;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool {
    private final int threadCount;
    private final Thread[] threads;
    private final Queue<Runnable> tasks;
    private final Object lock = new Object();
    private boolean stopRequest = false;

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
        synchronized (lock) {
            tasks.add(runnable);
            lock.notifyAll();
        }
    }

    @Override
    public void stop() {
        stopRequest = true;
    }

    class Worker implements Runnable {
        @Override
        public void run() {
            while (!stopRequest) {
                Runnable task;
                synchronized (lock) {
                    while(tasks.size() == 0) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = tasks.poll();
                }
                task.run();
            }
        }
    }
}
