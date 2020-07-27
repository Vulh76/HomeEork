package ru.stb.lesson12.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionService {

    /**
     * Получение списка наименований методов класса
     *
     * @param aClass {@link Class} тип класса
     * @return {@link List} список наименований методов
     */
    public static List<String> getClassMethods(Class<?> aClass) {
        return Arrays.stream(aClass.getDeclaredMethods()).map(Method::getName).collect(Collectors.toList());
    }

    /**
     * Получение списка наименований полей класса
     *
     * @param aClass {@link Class} тип класса
     * @return {@link List} список наименований полей
     */
    public static List<String> getClassFields(Class<?> aClass) {
        return Arrays.stream(aClass.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
    }

    /**
     * Получение списка наименований аннотаций метода
     *
     * @param method {@link Method} тип метода
     * @return {@link List} список наименований аннотаций
     */
    public static List<String> getClassAnnotations(Method method) {
        return Arrays.stream(method.getDeclaredAnnotations()).map(Annotation::toString).collect(Collectors.toList());
    }

    /**
     * Получение типа класса, которым параметризовано generic-поле класса
     *
     * @param aClass aClass {@link Class} тип класса
     * @param fieldName наименование поля класса
     * @return {@link Class} тип класса, которым параметризован generic поле
     * @throws NoSuchFieldException
     */
    public static Class<?> getGenericFieldType(Class<?> aClass, String fieldName) throws NoSuchFieldException {
        Field field = aClass.getDeclaredField(fieldName);
        ParameterizedType colorsGenericType = (ParameterizedType) field.getGenericType();
        return  (Class<?>) colorsGenericType.getActualTypeArguments()[0];
    }

}
