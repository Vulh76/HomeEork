package ru.stb.lesson10.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        //TaskCallable<String> taskCallable = new TaskCallable<>("Task");
        //Task<String> task = new Task<>(taskCallable);

        Task<String> task = new Task<>(() -> "TaskCall");

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //threads.add(new Thread(new TaskRunnable<>(task)));
            threads.add(new Thread(() -> System.out.println(task.get())));
        }

        try {
            for (Thread thread : threads) {
                thread.start();
                thread.join();
            }
        } catch (RuntimeException e) {
            e.getCause().printStackTrace();
        }
    }

    static class TaskCallable<T> implements Callable<T> {
        private final T state;

        public TaskCallable(T state) {
            this.state = state;
        }

        @Override
        public T call() throws Exception {
            return state;
        }
    }

    static class TaskRunnable<T> implements Runnable {
        private final Task<T> task;

        public TaskRunnable(Task<T> task) {
            this.task = task;
        }

        @Override
        public void run() {
            T result = task.get();
            System.out.println(result);
        }
    }
}
