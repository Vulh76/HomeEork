package ru.stb.lesson10.executionmanager.api;

public interface Context {
    // Добавление рабочего потока
    void addThread(Thread thread);
    // Возвращает количество задач, которые на текущий момент успешно выполнились
    int getCompletedTaskCount();
    // Возвращает количество задач, при выполнении которых произошел Exception
    int getFailedTaskCount();
    // Возвращает количество задач, выполнение которых было прервано методом interrupt()
    int getInterruptedTaskCount();
    // Отменяет выполнение задач, которые еще не начали выполняться
    void interrupt();
    // Возвращает true, если все задачи были выполнены или отменены, в противном случае - false
    boolean isFinished();
}
