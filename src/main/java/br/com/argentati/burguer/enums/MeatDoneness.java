package br.com.argentati.burguer.enums;

public enum MeatDoneness {
    MAL_PASSADO,
    AO_PONTO,
    BEM_PASSADO;

    public static MeatDoneness fromString(String value) {
        return switch (value) {
            case "MAL_PASSADO" -> MAL_PASSADO;
            case "AO_PONTO" -> AO_PONTO;
            case "BEM_PASSADO" -> BEM_PASSADO;
            default -> throw new IllegalArgumentException("Invalid value: " + value);
        };
    }

    public static String toString(MeatDoneness value) {
        return switch (value) {
            case MAL_PASSADO -> "MAL_PASSADO";
            case AO_PONTO -> "AO_PONTO";
            case BEM_PASSADO -> "BEM_PASSADO";
        };
    }
}
