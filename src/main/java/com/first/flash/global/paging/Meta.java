package com.first.flash.global.paging;

import java.util.Objects;

public record Meta(String cursor, boolean hasNext) {

    public static Meta from(final String nextCursor) {
        if (Objects.isNull(nextCursor) || nextCursor.isEmpty()) {
            return new Meta(null, false);
        }
        return new Meta(nextCursor, true);
    }
}
