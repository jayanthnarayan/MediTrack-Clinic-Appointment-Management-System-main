package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;

public final class Validator {
    private Validator() {}
    public static void notBlank(String s, String field) {
        if (s == null || s.isBlank()) throw new InvalidDataException(field + " cannot be blank");
    }
    public static void positive(int n, String field) {
        if (n <= 0) throw new InvalidDataException(field + " must be > 0");
    }
    public static void nonNegative(double d, String field) {
        if (d < 0) throw new InvalidDataException(field + " cannot be negative");
    }
}
