package com.first.flash.climbing.gym.exception.exceptions;

public class ClimbingGymNotFoundException extends RuntimeException {

    public ClimbingGymNotFoundException(final Long id) {
        super(String.format("아이디가 %s인 클라이밍장을 찾을 수 없습니다.", id));
    }
}
