package ru.stb.lesson05.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        Rectangle rectangle = new Rectangle(25, 15);
        Circle circle = new Circle(50);

        // Методы класса Rectangle
        printClass(rectangle.getClass());

        // Методы класса Circle
        printClass(Circle.class);

        // Геттеры класса
        printGetters(circle.getClass());

        // Проверка полей класса Week
        Week week = new Week();
        Field[] fields = Week.class.getFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if(field.getType().equals(String.class) && Modifier.isStrict(modifiers) && Modifier.isFinal(modifiers) ) {
                if(field.getName().compareToIgnoreCase((String) field.get(week)) != 0) {
                    System.out.printf("Неверное значение поля %s%n", field.getName());
                }
            }
        }
    }

    // Распечатка методов класса и его предкв, а так же реализуемых интерфейсов
    private static void printClass(Class<?> clazz) {
        Class<?> superClass = clazz.getSuperclass();
        if(superClass != null)
            printClass(superClass);

        System.out.println();
        System.out.println("Класс " + clazz.getName());

        Class<?>[] interfaces =  clazz.getInterfaces();
        for (Class<?> interfaze : interfaces) {
            System.out.printf("Интерфейс: %s%n", interfaze.getName());
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.printf("Метод: %s%n", method.getName());
        }
    }

    // Распечатка геттеров класса
    private static void printGetters(Class<?> clazz) {
        System.out.println();
        System.out.println("Класс " + clazz.getName());

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if(method.getName().contains("get") && Modifier.isPublic(modifiers)) {
                System.out.printf("Getter: %s%n", method.getName());
            }
        }
    }
}
