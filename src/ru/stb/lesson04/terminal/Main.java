package ru.stb.lesson04.terminal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Terminal terminal  = new TerminalImpl();
        TerminalConsoleInterface consoleInterface = new TerminalConsoleInterface(terminal);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            consoleInterface.authorize();

            boolean session = true;
            while (session) {
                System.out.println();
                System.out.println("Выберите операцию:");
                System.out.println("1 - проверить баланс");
                System.out.println("2 - положить деньги на счет");
                System.out.println("3 - снять деньги со счета");
                System.out.println("0 - завершить обслуживание");
                System.out.println();

                int num = scanner.nextInt();

                switch (num) {
                    case 1:
                        consoleInterface.checkAccount();
                        break;
                    case 2:
                        consoleInterface.toDeposit();
                        break;
                    case 3:
                        consoleInterface.withdraw();
                        break;
                    case 0:
                        session = false;
                        break;
                }
            }
            terminal.endSession();
            System.out.println("До свидания!");
            System.out.println();
        }
    }
}
