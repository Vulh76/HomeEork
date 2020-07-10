package ru.stb.lesson10.executionmanager;

import ru.stb.lesson10.executionmanager.api.Context;

import java.util.ArrayList;
import java.util.List;

public class ContextImpl implements Context {
    private final List<Thread> threads = new ArrayList<>();

    @Override
    public void addThread(Thread thread) {
        threads.add(thread);
    }

    @Override
    public int getCompletedTaskCount() {
        int count = 0;
        for (Thread thread : threads) {
            if(!thread.isAlive())
                count++;
        }
        return count;
    }

    @Override
    public int getFailedTaskCount() {
        int count = 0;
        for (Thread thread : threads) {

                count++;
        }
        return count;
    }

    @Override
    public int getInterruptedTaskCount() {
        int count = 0;
        for (Thread thread : threads) {
            if(thread.isInterrupted())
                count++;
        }
        return count;
    }

    @Override
    public void interrupt() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    @Override
    public boolean isFinished() {
        for (Thread thread : threads) {
            if(thread.getState() != Thread.State.TERMINATED)
                return false;
        }
        return true;
    }
}
