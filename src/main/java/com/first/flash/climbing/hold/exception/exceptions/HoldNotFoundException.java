package com.first.flash.climbing.hold.exception.exceptions;

import com.first.flash.climbing.sector.exception.exceptions.SectorNotFoundException;
import com.first.flash.global.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class HoldNotFoundException extends RuntimeException {

    public HoldNotFoundException(final Long id) {
        super(String.format("아이디가 %s인 홀드를 찾을 수 없습니다.", id));
    }
}
