package ru.stb.lesson05.proxy;

public interface Calculator {
    @Cache
    int squared(int val);

    int doubling(int val);
}
