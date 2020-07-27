package ru.stb.lesson05.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Обработчик динамичесокого прокси для кэширования вычислений
 */
public class CacheHandler implements InvocationHandler {
    private final Object original;
    private final Map<Integer, Integer> cache;

    public CacheHandler(Object original) {
        this.original = original;
        this.cache = new CacheMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(args == null || args.length == 0)
            throw new IllegalArgumentException("Функции требуется параметр");

        if(method.isAnnotationPresent(Cache.class)) {
            int arg = (int) args[0];
            Integer result = cache.get(arg);
            if (result == null) {
                result = (Integer) method.invoke(original, arg);
                cache.put(arg, result);
            }
            return result;
        }
        return method.invoke(original, args);
    }
}
