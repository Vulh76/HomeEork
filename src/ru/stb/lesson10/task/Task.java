package ru.stb.lesson10.task;

import java.util.concurrent.Callable;

public class Task<T> {
    private final Callable<? extends T> callable;
    private volatile T result = null;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {
        if (result == null) {
            synchronized (this) {
                if (result == null) {
                    try {
                        result = callable.call();
                        return result;
                    } catch (Exception e) {
                        throw new MyTaskException(e);
                    }
                }
            }
        }
        return result;
    }
}
