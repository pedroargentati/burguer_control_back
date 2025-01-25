package br.com.argentati.burguer.enums;

public enum Status {

    REALIZADO,
    EM_PREPARACAO,
    PRONTO;

    public static Status fromString(String value) {
        return switch (value.toUpperCase()) {
            case "REALIZADO" -> REALIZADO;
            case "EM_PREPARACAO" -> EM_PREPARACAO;
            case "PRONTO" -> PRONTO;
            default -> throw new IllegalArgumentException("Invalid value: " + value);
        };
    }

    public static String toString(Status value) {
        return switch (value) {
            case REALIZADO -> "REALIZADO";
            case EM_PREPARACAO -> "EM_PREPARACAO";
            case PRONTO -> "PRONTO";
        };
    }

}
