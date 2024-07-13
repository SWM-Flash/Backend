package com.first.flash.climbing.problem.exception.exceptions;

import java.util.UUID;

public class QueryProblemNotFoundException extends RuntimeException {

    public QueryProblemNotFoundException(final UUID problemId) {
        super(String.format("아이디가 %s인 문제를 찾을 수 없습니다.", problemId));
    }
}
