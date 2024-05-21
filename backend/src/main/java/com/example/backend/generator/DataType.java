package com.example.backend.generator;

public enum DataType {
    EMPLOYEE("emp"),
    CUSTOMER("cus"),
    MOVIE_DATA("mov"),
    TICKET("tic"),
    SEAT("seat");

    private final String stringValue;

    DataType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    // Method to convert string to enum
    public static DataType fromString(String text) {
        for (DataType value : DataType.values()) {
            if (value.stringValue.equalsIgnoreCase(text)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum constant with string value: " + text);
    }
}
