package ru.stb.lesson11;

import java.util.HashMap;
import java.util.Map;

public class jitgc {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10_000_000; i++) {
            map.put(i, "Value-" + i);
        }

        System.out.println("Количество записей: " + map.size());
    }
}
