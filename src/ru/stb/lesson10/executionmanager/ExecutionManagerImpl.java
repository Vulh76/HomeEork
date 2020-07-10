package ru.stb.lesson10.executionmanager;

import ru.stb.lesson10.executionmanager.api.*;
import ru.stb.lesson09.threadpool.FixedThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutionManagerImpl implements ExecutionManager {
    //private final FixedThreadPool threadPool;
    private final ExecutorService threadPool;
    private final Context context;

    public ExecutionManagerImpl() {
        //threadPool = new FixedThreadPool(Runtime.getRuntime().availableProcessors());
        threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        context = new ContextImpl();
    }

    @Override
    public Context execute(Runnable callback, Runnable... tasks) throws ExecutionException, InterruptedException {
        Context context = new ContextImpl();

        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            context.addThread(thread);
            thread.start();
        }
        //return context;

        List<Future> futures = new ArrayList<>();
        for (Runnable task : tasks) {
            futures.add(threadPool.submit(task));
        }
        callback.run();
        for (Future future : futures) {
            future.get();
        }
        threadPool.shutdown();
        return context;
    }
}
