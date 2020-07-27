package ru.stb.lesson04.content;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите адрес сайта:");
        while (true) {
            String url = scanner.nextLine();

            try {
                Content.readConnect(url);
                break;
            }
            catch (MalformedURLException e) {
                System.out.println("Неверный формат URL! Повторите ввод:");
            }
            catch (IOException e) {
                System.out.println("Ошибка соединения! Введите другой адрес:");
            }
        }
    }
}
