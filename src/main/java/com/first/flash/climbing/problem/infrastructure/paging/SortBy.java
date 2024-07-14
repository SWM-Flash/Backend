package com.first.flash.climbing.problem.infrastructure.paging;

public enum SortBy {

    VIEWS("views"),
    DIFFICULTY("difficulty");

    private static final SortBy DEFAULT_SORT_BY = VIEWS;
    private final String value;

    SortBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SortBy from(final String value) {
        for (SortBy sortBy : SortBy.values()) {
            if (sortBy.value.equalsIgnoreCase(value)) {
                return sortBy;
            }
        }
        return DEFAULT_SORT_BY;
    }
}
