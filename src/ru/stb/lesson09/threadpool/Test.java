package ru.stb.lesson09.threadpool;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        int procCount = Runtime.getRuntime().availableProcessors();

        FixedThreadPool fixedThreadPool = new FixedThreadPool(procCount);
        fixedThreadPool.start();
        fixedThreadPool.execute(() -> randomTask("FixedThreadPool. Задача 1 выполнилась!"));
        fixedThreadPool.execute(() -> randomTask("FixedThreadPool. Задача 2 выполнилась!"));
        fixedThreadPool.execute(() -> randomTask("FixedThreadPool. Задача 3 выполнилась!"));
        fixedThreadPool.execute(() -> randomTask("FixedThreadPool. Задача 4 выполнилась!"));
        fixedThreadPool.execute(() -> randomTask("FixedThreadPool. Задача 5 выполнилась!"));
        fixedThreadPool.execute(() -> randomTask("FixedThreadPool. Задача 6 выполнилась!"));
        fixedThreadPool.execute(() -> randomTask("FixedThreadPool. Задача 7 выполнилась!"));
        fixedThreadPool.execute(() -> randomTask("FixedThreadPool. Задача 8 выполнилась!"));

        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(procCount, procCount * 2);
        scalableThreadPool.start();
        scalableThreadPool.execute(() -> randomTask("ScalableThreadPool. Задача 1 выполнилась!"));
        scalableThreadPool.execute(() -> randomTask("ScalableThreadPool. Задача 2 выполнилась!"));
        scalableThreadPool.execute(() -> randomTask("ScalableThreadPool. Задача 3 выполнилась!"));
        scalableThreadPool.execute(() -> randomTask("ScalableThreadPool. Задача 4 выполнилась!"));
        scalableThreadPool.execute(() -> randomTask("ScalableThreadPool. Задача 5 выполнилась!"));
        scalableThreadPool.execute(() -> randomTask("ScalableThreadPool. Задача 6 выполнилась!"));
        scalableThreadPool.execute(() -> randomTask("ScalableThreadPool. Задача 7 выполнилась!"));
        scalableThreadPool.execute(() -> randomTask("ScalableThreadPool. Задача 8 выполнилась!"));
        scalableThreadPool.execute(() -> randomTask("ScalableThreadPool. Задача 9 выполнилась!"));
    }

    static private void randomTask(String msg) {
        double result = 0;
        int random = new Random().nextInt(10000) + 1;
        for (int i = 1; i < random; i++) {
            for (int j = random; j > 0; j--) {
                result += Math.sin((double) i / j) * Math.cos((double) j / i) * Math.tan((double) i / random);
            }
        }
        System.out.println(msg + " Результат: " + result);
    }
}
