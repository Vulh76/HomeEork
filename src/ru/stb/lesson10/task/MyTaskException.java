package ru.stb.lesson10.task;

public class MyTaskException extends RuntimeException {
    public MyTaskException() {
    }

    public MyTaskException(String message) {
        super(message);
    }

    public MyTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyTaskException(Throwable cause) {
        super(cause);
    }

    public MyTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
