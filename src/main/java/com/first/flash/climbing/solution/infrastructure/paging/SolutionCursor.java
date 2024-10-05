package com.first.flash.climbing.solution.infrastructure.paging;

import com.first.flash.climbing.problem.exception.exceptions.InvalidCursorException;
import java.util.Base64;
import java.util.Objects;

public record SolutionCursor(String cursorValue, Long lastId) {

    private static final String DEFAULT_SEPARATOR = ";";
    private static final int CURSOR_VALUE_INDEX = 0;
    private static final int LAST_ID_INDEX = 1;
    private static final int PARTS_LENGTH = 2;

    public String encode() {
        String combined = combineValues();
        return Base64.getEncoder()
                     .encodeToString(combined.getBytes());
    }

    public static SolutionCursor decode(final String encoded) {
        if (Objects.isNull(encoded) || encoded.isEmpty()) {
            return null;
        }

        try {
            String decoded = new String(Base64.getDecoder()
                                              .decode(encoded));
            String[] parts = decoded.split(DEFAULT_SEPARATOR);
            if (parts.length != PARTS_LENGTH) {
                throw new IllegalArgumentException();
            }

            return new SolutionCursor(parts[CURSOR_VALUE_INDEX],
                Long.parseLong(parts[LAST_ID_INDEX]));
        } catch (RuntimeException exception) {
            throw new InvalidCursorException();
        }
    }

    private String combineValues() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cursorValue)
                     .append(DEFAULT_SEPARATOR)
                     .append(lastId.toString());
        return stringBuilder.toString();
    }
}
