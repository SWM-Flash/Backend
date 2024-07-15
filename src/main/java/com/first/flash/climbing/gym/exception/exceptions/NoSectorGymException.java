package com.first.flash.climbing.gym.exception.exceptions;

public class NoSectorGymException extends RuntimeException {

    public NoSectorGymException(final Long id) {
        super(String.format("아이디가 %d인 클라이밍장엔 섹터가 존재하지 않습니다.", id));
    }
}
