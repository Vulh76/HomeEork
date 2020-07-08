package ru.stb.lesson9.threadpool;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ScalableThreadPool implements ThreadPool {
    private final int minThreadCount;
    private final int maxThreadCount;
    private final List<Thread> threads;
    private final Queue<Runnable> tasks;
    private final Object lock = new Object();
    private boolean stopRequest = false;

    public ScalableThreadPool(int minThreadCount, int maxThreadCount) {
        this.minThreadCount = minThreadCount;
        this.maxThreadCount = maxThreadCount;
        tasks = new ArrayDeque<>();
        threads = new ArrayList<>(minThreadCount);
        for (int i = 0; i < minThreadCount; i++) {
            threads.add(new Thread(new Worker()));
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
            if(tasks.size() > threads.size() && threads.size() < maxThreadCount) {
                Thread thread = new Thread(new Worker());
                threads.add(thread);
                thread.start();
            }
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
                    if(tasks.size() < threads.size() && threads.size() > minThreadCount) {
                        threads.remove(Thread.currentThread());
                        stopRequest = true;
                    }
                }
                task.run();
            }
        }
    }
}
