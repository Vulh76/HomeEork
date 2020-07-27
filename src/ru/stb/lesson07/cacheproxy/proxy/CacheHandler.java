package ru.stb.lesson07.cacheproxy.proxy;

import ru.stb.lesson07.cacheproxy.proxy.annotation.Cache;
import ru.stb.lesson07.cacheproxy.proxy.utils.CacheKey;
import ru.stb.lesson07.cacheproxy.proxy.utils.CacheMap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CacheHandler implements InvocationHandler {
    private final Object original;
    private final String cacheDir;
    private CacheMap<CacheKey, Object> cacheData;

    /**
     * Конструктор принимает общие параметры кэширования
     *
     * @param original обект, для которого создается прокси
     * @param cacheDir каталог, в тором будут создаваться файлы кэша, если выбрано сохраниений кэша в файл
     * @param cacheSize максимальный размер кэша
     */
    public CacheHandler(Object original, String cacheDir, int cacheSize) {
        this.original = original;
        this.cacheDir = cacheDir;
        this.cacheData = new CacheMap<>(cacheSize);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(!method.isAnnotationPresent(Cache.class))
            return method.invoke(original, args);

        Cache cacheAnnotation = method.getAnnotation(Cache.class);
        CacheKey cacheKey = new CacheKey(method.getName(), args);
        Object cacheValue = cacheData.get(cacheKey);
        if(cacheValue != null) return cacheValue;

        Object result = method.invoke(original, args);
        cacheData.put(cacheKey, result);

        saveCache(cacheDir + "\\" + method.getName() + ".cache");
        loadCache(cacheDir + "\\" + method.getName() + ".cache");

        return result;
    }

    public void saveCache(String fileName) throws IOException {
        try(
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)
        ) {
            out.writeObject(cacheData);
        }
    }

    public void loadCache(String fileName) throws IOException, ClassNotFoundException {
        try(
                FileInputStream fileInputStream = new  FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileInputStream)
        ) {
            cacheData = (CacheMap<CacheKey, Object>) in.readObject();
        }
    }
}
