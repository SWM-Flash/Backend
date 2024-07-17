package com.first.flash.climbing.sector.exception.exceptions;

public class InvalidRemovalDateException extends RuntimeException {

    public InvalidRemovalDateException() {
        super("탈거일이 세팅일보다 빠를 수 없습니다.");
    }
}
