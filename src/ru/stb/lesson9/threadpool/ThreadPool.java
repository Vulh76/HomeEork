package ru.stb.lesson9.threadpool;

public interface ThreadPool {
    void start();
    void execute(Runnable runnable);
    void stop();
}
