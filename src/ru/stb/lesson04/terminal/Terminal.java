package ru.stb.lesson04.terminal;

public interface Terminal {
    // Авторизация по PIN-коду
    void authorize(int pin) throws TerminalException;

    // Завершить сессию
    void endSession();

    // Проверить счет
    double checkAccount() throws TerminalException;

    // Положить указнную сумму
    void toDeposit(int amount) throws TerminalException;

    // Снять указанную сумму
    void withdraw(int amount) throws TerminalException;
}
