package ru.stb.lesson12.proxy.annotation;

import ru.stb.lesson12.proxy.utils.CacheType;

import java.awt.*;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    CacheType cacheType() default CacheType.FILE;
    String fileNamePrefix() default "data";
    int listLimit() default Integer.MAX_VALUE;
    boolean zip() default false;
    Class<?>[] identityBy() default {};
}
