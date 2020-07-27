package ru.stb.lesson05.proxy;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheMap<K, V> extends LinkedHashMap<K, V> {
    private static final int DEFAULT_CACHE_SIZE = 100;
    private final int cacheSize;

    public CacheMap() {
        this.cacheSize = DEFAULT_CACHE_SIZE;
    }

    public CacheMap(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > cacheSize;
    }
}
