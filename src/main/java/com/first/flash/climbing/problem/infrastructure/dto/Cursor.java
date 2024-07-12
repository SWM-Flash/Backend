package com.first.flash.climbing.problem.infrastructure.dto;

import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

public record Cursor(String sortBy, String cursorValue, UUID lastId) {

    private static final String DEFAULT_SEPARATOR = ":";
    private static final int SORT_BY_INDEX = 0;
    private static final int CURSOR_VALUE_INDEX = 1;
    private static final int LAST_ID_INDEX = 2;

    public String encode() {
        String combined =
            sortBy + DEFAULT_SEPARATOR + cursorValue + DEFAULT_SEPARATOR + lastId.toString();
        return Base64.getEncoder()
                     .encodeToString(combined.getBytes());
    }

    public static Cursor decode(final String encoded) {
        if (Objects.isNull(encoded) || encoded.isEmpty()) {
            return null;
        }

        String decoded = new String(Base64.getDecoder()
                                          .decode(encoded));
        String[] parts = decoded.split(DEFAULT_SEPARATOR);
        return new Cursor(parts[SORT_BY_INDEX], parts[CURSOR_VALUE_INDEX],
            UUID.fromString(parts[LAST_ID_INDEX]));
    }
}
