package br.com.argentati.burguer.converter;

import br.com.argentati.burguer.enums.MeatDoneness;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MeatDonenessConverter implements AttributeConverter<MeatDoneness, String> {

    @Override
    public String convertToDatabaseColumn(MeatDoneness attribute) {
        return attribute != null ? attribute.name().toLowerCase() : null;
    }

    @Override
    public MeatDoneness convertToEntityAttribute(String dbData) {
        return dbData != null ? MeatDoneness.fromString(dbData.toUpperCase()) : null;
    }
}

