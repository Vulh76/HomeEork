package ru.stb.lesson10.executionmanager;

import ru.stb.lesson10.executionmanager.api.*;
import ru.stb.tools.HardTask;

import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) {
        ExecutionManager manager = new ExecutionManagerImpl();

        try {
            Context context = manager.execute(
                    () -> System.out.println("All Done!"),
                    () -> System.out.println("Task 1, Результат: " + HardTask.randomTask(1000)),
                    () -> System.out.println("Task 2, Результат: " + HardTask.randomTask(1000)),
                    () -> System.out.println("Task 3, Результат: " + HardTask.randomTask(1000)),
                    () -> System.out.println("Task 4, Результат: " + HardTask.randomTask(1000)),
                    () -> System.out.println("Task 5, Результат: " + HardTask.randomTask(1000)),
                    () -> System.out.println("Task 6, Результат: " + HardTask.randomTask(1000)),
                    () -> System.out.println("Task 7, Результат: " + HardTask.randomTask(1000)),
                    () -> System.out.println("Task 8, Результат: " + HardTask.randomTask(1000)),
                    () -> System.out.println("Task 9, Результат: " + HardTask.randomTask(1000))
            );

            //context.interrupt();

            Thread.sleep(1000);

            System.out.println("Завершено: " + context.getCompletedTaskCount());
            System.out.println("Прервано с ошибкой: " + context.getFailedTaskCount());
            System.out.println("Отменено: " + context.getInterruptedTaskCount());
            System.out.println("Все задачи завершены?: " + context.isFinished());

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}