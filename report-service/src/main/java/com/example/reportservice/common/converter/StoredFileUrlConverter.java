package com.example.reportservice.common.converter;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StoredFileUrlConverter implements AttributeConverter<List<String>, String> {

    private final String Delimiter = ", ";
    @Override
    public String convertToDatabaseColumn(final List<String> attribute) {
        return attribute == null? null : attribute.stream().collect(Collectors.joining(Delimiter));
    }

    @Override
    public List<String> convertToEntityAttribute(final String dbData) {
        return dbData == null? null : Arrays.asList(dbData.split(Delimiter));
    }
}
