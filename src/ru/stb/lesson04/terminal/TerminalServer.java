package ru.stb.lesson04.terminal;

import java.util.Random;

public class TerminalServer {
    // Остаток на счете
    private double balance;
    // Random для генерации случайных сбоев
    private static final Random random = new Random();

    public TerminalServer() {
        // Кладем на счет случайную сумму
        balance = random.nextInt(100000) + 1000;
    }

    public double getBalance() throws ServerConnectionException {
        randomError();
        return balance;
    }

    public void toDeposit(double amount) throws ServerConnectionException {
        randomError();
        balance += amount;
    }

    public void withdraw(double amount) throws ServerConnectionException, ServerBalanceException {
        randomError();

        if(amount > balance)
            throw new ServerBalanceException("Недостаточно средств на счете");

        balance -= amount;
    }

    private void randomError() throws ServerConnectionException {
        if(random.nextInt(100) < 5)
            throw new ServerConnectionException("Ошибка соединения с сервером. Попробуйте выполнить операцию позже");
    }
}
