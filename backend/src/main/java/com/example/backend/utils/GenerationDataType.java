package com.example.backend.utils;

public enum GenerationDataType {
    EMPLOYEE("emp"),
    CUSTOMER("cus"),
    MOVIE("mov"),
    TICKET("tic"),
    SEAT("seat"),
    CINEMA("cin");

    private final String stringValue;

    GenerationDataType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static GenerationDataType fromString(String text) {
        for (GenerationDataType value : GenerationDataType.values()) {
            if (value.stringValue.equalsIgnoreCase(text)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum constant with string value: " + text);
    }
}
