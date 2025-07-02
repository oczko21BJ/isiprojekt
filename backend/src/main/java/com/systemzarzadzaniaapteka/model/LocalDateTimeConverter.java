package com.systemzarzadzaniaapteka.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Klasa konwertera między LocalDateTime a Timestamp w systemie zarządzania apteką.
 * 
 * <p>Klasa LocalDateTimeConverter implementuje interfejs AttributeConverter,
 * umożliwiając konwersję między typami LocalDateTime i Timestamp podczas operacji na bazie danych.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
// Konwerter LocalDateTime -> Timestamp (i odwrotnie)
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute == null ? null : Timestamp.valueOf(attribute);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        return dbData == null ? null : dbData.toLocalDateTime();
    }
}
