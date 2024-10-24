package com.first.flash.global.paging;

import java.util.Objects;

public record Meta(String cursor, boolean hasNext, Integer count) {

    public static Meta of(final String nextCursor, final Integer count) {
        if (Objects.isNull(nextCursor) || nextCursor.isEmpty()) {
            return new Meta(null, false, count);
        }
        return new Meta(nextCursor, true, count);
    }
}
