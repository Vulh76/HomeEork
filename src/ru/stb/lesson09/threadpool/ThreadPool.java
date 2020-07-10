package ru.stb.lesson09.threadpool;

public interface ThreadPool {
    void start();
    void execute(Runnable runnable);
    void stop();
}
