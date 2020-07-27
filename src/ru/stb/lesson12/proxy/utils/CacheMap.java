package ru.stb.lesson12.proxy.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheMap<K, V> extends LinkedHashMap<K, V> {
    private int cacheSize;

    public static final int DEFAULT_CACHE_SIZE = 50;

    public CacheMap() {
        //this.cacheSize = DEFAULT_CACHE_SIZE;
    }

    public CacheMap(int cacheSize) {
         this.cacheSize = cacheSize;
    }

    public CacheMap(int cacheSize, int initialCapacity) {
        super(Math.min(initialCapacity, cacheSize));
        this.cacheSize = cacheSize;
    }

    public CacheMap(int cacheSize, int initialCapacity, float loadFactor) {
        super(Math.min(initialCapacity, cacheSize), loadFactor);
        this.cacheSize = cacheSize;
    }

    public CacheMap(int initialCapacity, float loadFactor, boolean accessOrder, int cacheSize) {
        super(Math.min(initialCapacity, cacheSize), loadFactor, accessOrder);
        this.cacheSize = cacheSize;
    }

    public CacheMap(Map<? extends K, ? extends V> m, int cacheSize) {
        super(m);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > cacheSize;
    }
}
