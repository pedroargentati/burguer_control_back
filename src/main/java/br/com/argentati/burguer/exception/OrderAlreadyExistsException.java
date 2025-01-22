package br.com.argentati.burguer.exception;

public class OrderAlreadyExistsException extends RuntimeException {

    public OrderAlreadyExistsException() {
        super();
    }

    public OrderAlreadyExistsException(String message) {
        super(message);
    }

    public OrderAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
