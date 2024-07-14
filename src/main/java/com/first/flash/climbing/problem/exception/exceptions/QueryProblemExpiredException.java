package com.first.flash.climbing.problem.exception.exceptions;

import java.util.UUID;

public class QueryProblemExpiredException extends RuntimeException {

    public QueryProblemExpiredException(final UUID problemId) {
        super(String.format("아이디가 %s인 문제가 만료되었습니다.", problemId));
    }
}
