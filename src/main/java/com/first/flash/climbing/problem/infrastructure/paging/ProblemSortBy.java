package com.first.flash.climbing.problem.infrastructure.paging;

public enum ProblemSortBy {

    VIEWS("views"),
    DIFFICULTY("difficulty"),
    RECOMMEND("recommend");

    private static final ProblemSortBy DEFAULT_SORT_BY = RECOMMEND;
    private final String value;

    ProblemSortBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProblemSortBy from(final String value) {
        for (ProblemSortBy problemSortBy : ProblemSortBy.values()) {
            if (problemSortBy.value.equalsIgnoreCase(value)) {
                return problemSortBy;
            }
        }
        return DEFAULT_SORT_BY;
    }
}
