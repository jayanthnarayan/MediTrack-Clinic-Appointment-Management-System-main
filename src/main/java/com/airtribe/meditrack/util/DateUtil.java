package com.airtribe.meditrack.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    private DateUtil() {}
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static LocalDateTime parseDateTime(String s) { return LocalDateTime.parse(s, FMT); }
    public static String formatDateTime(LocalDateTime dt) { return dt.format(FMT); }
}

