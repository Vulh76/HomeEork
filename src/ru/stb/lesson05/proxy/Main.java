package ru.stb.lesson05.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        
        Calculator calculator = new CalculatorImpl();
        
        Class<?> proxyClass = Proxy.getProxyClass(
                Calculator.class.getClassLoader(),
                //new Class<?>[]{Calculator.class}
                Calculator.class
        );

        try {
            Calculator calculatorProxy = (Calculator) proxyClass.getConstructor(InvocationHandler.class)
                    .newInstance(new CacheHandler(calculator));

            int result;
            result = calculatorProxy.squared(2);
            result = calculatorProxy.doubling(1);
            result = calculatorProxy.squared(4);
            result = calculatorProxy.doubling(2);
            result = calculatorProxy.squared(2);
            result = calculatorProxy.doubling(1);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
