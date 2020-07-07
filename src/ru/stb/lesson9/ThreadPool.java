package ru.stb.lesson9;

public interface ThreadPool {
    void start();
    void execute(Runnable runnable);
}
