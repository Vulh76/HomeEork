package ru.stb.lesson04.terminal;

public class TerminalImpl implements Terminal{
    private final TerminalServer server;
    private final PinValidator pinValidator;

    public TerminalImpl() {
        this.server = new TerminalServer();
        this.pinValidator = new PinValidator();
    }

    @Override
    public void authorize(int pin) throws TerminalException {
        try {
            this.pinValidator.validate(pin);
        }
        catch (PinValidateException e) {
            throw new TerminalException("Неверный PIN-код. Попробуйте еще раз", e);
        }
        catch (PinLockException e) {
            throw new TerminalException("Система заблокирована. Повторный ввод будет возможен через " + e.timeRemaining() + " сек.", e);
        }
    }

    @Override
    public void endSession() {
        pinValidator.reset();
    }

    @Override
    public double checkAccount() throws TerminalException {
        try {
            pinValidator.check();
            return server.getBalance();
        }
        catch (PinValidateException e) {
            throw e;
        }
    }

    @Override
    public void toDeposit(int amount) throws TerminalException {
        try {
            pinValidator.check();
            checkAmount(amount);

            putMoney(amount);
            server.toDeposit(amount);
        }
        catch (ServerConnectionException e) {
            throw new TerminalException("Ошибка соединения с сервером. Попробуйте выполнить операцию позже", e);
        }
        catch (TerminalAmountException e) {
            throw new TerminalException("Сумма должня быть кратна 100 рублям", e);
        }
    }

    @Override
    public void withdraw(int amount) throws TerminalException {
        try {
            pinValidator.check();
            checkAmount(amount);

            givMoney(amount);
            server.withdraw(amount);
        }
        catch (ServerConnectionException e) {
            throw new TerminalException("Ошибка соединения с сервером. Попробуйте выполнить операцию позже", e);
        }
        catch (TerminalAmountException e) {
            throw new TerminalException("Сумма должня быть кратна 100 рублям", e);
        }
    }

    // Выдать деньги
    private void givMoney(int amount) {
    }

    // Забрать деньги (пересчитать, проверить...)
    private void putMoney(int amount) {
    }

    private static void checkAmount(int amount) throws TerminalAmountException {
        if(amount % 100 > 0)
            throw new TerminalAmountException(amount);
    }
}
