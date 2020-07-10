package ru.stb.lesson10.executionmanager.api;

import ru.stb.lesson10.executionmanager.api.Context;

import java.util.concurrent.ExecutionException;

public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks) throws ExecutionException, InterruptedException;
}
