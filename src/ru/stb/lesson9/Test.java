package ru.stb.lesson9;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        FixedThreadPool fixedThreadPool = new FixedThreadPool(4);
        fixedThreadPool.start();
        fixedThreadPool.execute(() -> System.out.println("Задача 1 выполнилась!"));
        fixedThreadPool.execute(() -> System.out.println("Задача 2 выполнилась!"));
        fixedThreadPool.execute(() -> System.out.println("Задача 3 выполнилась!"));
        fixedThreadPool.execute(() -> System.out.println("Задача 4 выполнилась!"));
        fixedThreadPool.execute(() -> System.out.println("Задача 5 выполнилась!"));
        fixedThreadPool.execute(() -> System.out.println("Задача 6 выполнилась!"));


        while (true) {}
    }

    private void randomTask() {
        int random = new Random().nextInt();
        for (int i = 0; i < random; i++) {
            for (int j = 0; j < random; j++) {

            }
        }
    }
}
