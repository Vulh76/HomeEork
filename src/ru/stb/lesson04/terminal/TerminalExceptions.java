package ru.stb.lesson04.terminal;


class TerminalException extends Exception {
    public TerminalException() {
    }

    public TerminalException(String message) {
        super(message);
    }

    public TerminalException(String message, Throwable cause) {
        super(message, cause);
    }
}

class PinValidateException extends TerminalException {
}

class PinLockException extends TerminalException {
    // Время, оставшееся до окончания блокировки
    private final long timeRemaining;

    public PinLockException(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public long timeRemaining() { return timeRemaining; }
}

class ServerConnectionException extends TerminalException {
    public ServerConnectionException(String message) {
        super(message);
    }
}

class ServerBalanceException extends TerminalException {
    public ServerBalanceException(String message) {
        super(message);
    }
}

class TerminalAmountException extends TerminalException {
    // Запрашиваемая или вносимая сумма
    private final int amount;

    public TerminalAmountException(int amount) {
        this.amount = amount;
    }

    public int amount() { return amount; }
}
