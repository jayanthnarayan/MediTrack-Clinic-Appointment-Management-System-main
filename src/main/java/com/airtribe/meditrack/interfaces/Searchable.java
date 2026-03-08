package com.airtribe.meditrack.interfaces;

public interface Searchable {

    boolean matches(String query);
    default boolean matchesAny(String... terms) {
        for (String t : terms) if (matches(t)) return true;
        return false;
    }

}
