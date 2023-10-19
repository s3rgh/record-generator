package com.adeo.syn.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductDataFormat {
    BU, PARTNER;

    @JsonValue
    @Override
    public String toString() {
        return name().toLowerCase();
    }

    @JsonCreator
    public ProductDataFormat fromString(String value) {
        return ProductDataFormat.valueOf(value.toUpperCase());
    }
}
