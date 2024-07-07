package com.example.backend.utils;

public enum DataType {
    EMPLOYEE("emp"),
    CUSTOMER("cus"),
    MOVIE("mov"),
    TICKET("tic"),
    SEAT("seat"),
    CINEMA("cin");

    private final String stringValue;

    DataType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static DataType fromString(String text) {
        for (DataType value : DataType.values()) {
            if (value.stringValue.equalsIgnoreCase(text)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum constant with string value: " + text);
    }
}
