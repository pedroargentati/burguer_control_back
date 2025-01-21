package br.com.argentati.burguer.exception;

public class BurguerControlException extends Exception {

    public BurguerControlException() {
        super();
    }

    public BurguerControlException(String message) {
        super(message);
    }

    public BurguerControlException(String message, Throwable cause) {
        super(message, cause);
    }
}
