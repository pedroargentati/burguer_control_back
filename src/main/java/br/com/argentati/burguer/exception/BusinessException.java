package br.com.argentati.burguer.exception;

public class BusinessException extends BurguerControlException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
