package ru.stb.lesson04.terminal;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class TerminalConsoleInterface {
    private final Scanner scanner;
    private final Terminal terminal;

    public TerminalConsoleInterface(@NotNull Terminal terminal) {
        this.scanner = new Scanner(System.in);
        this.terminal = terminal;
    }

    public void authorize() {
        System.out.println("Введите PIN-код:");
        int pin = getIntFromConsole();

        try {
            terminal.authorize(pin);
        }
        catch (TerminalException e) {
            System.out.println(e.getMessage());
            authorize();
        }
    }

    public void checkAccount() {
        try {
            double amount = terminal.checkAccount();
            System.out.println("Сумма на счету: " + amount + " рублей");
        }
        catch (PinValidateException e) {
            authorize();
        }
        catch (TerminalException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toDeposit() {
        System.out.println("Внесите необходимую сумму. Принимаются купюры минимальным достоинством 100 рублей");
        int amount = scanner.nextInt();

        try {
            terminal.toDeposit(amount);
            System.out.println("Средства внесены успешно");
        }
        catch (PinValidateException e) {
            authorize();
        }
        catch (TerminalException e) {
            System.out.println(e.getMessage());
        }
    }

    public void withdraw() {
        System.out.println("Введите сумму. Минимальное достоинство выдаваемых купюр 100 рублей");
        int amount = scanner.nextInt();

        try {
            terminal.withdraw(amount);
            System.out.println("Заберите деньги");
        }
        catch (PinValidateException e) {
            authorize();
        }
        catch (TerminalException e) {
            System.out.println(e.getMessage());
        }
    }

    private int getIntFromConsole() {
        while (true) {
            if(scanner.hasNextInt()) {
                return scanner.nextInt();
            }
            else {
                System.out.println("Ошибка ввода. Попробуйте еще раз:");
                scanner.next();
            }
        }
    }
}
