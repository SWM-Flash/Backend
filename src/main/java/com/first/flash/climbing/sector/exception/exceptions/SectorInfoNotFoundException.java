package com.first.flash.climbing.sector.exception.exceptions;

public class SectorInfoNotFoundException extends RuntimeException {

    public SectorInfoNotFoundException(final Long id) {
        super(String.format("아이디가 %s인 섹터 정보를 찾을 수 없습니다.", id));
    }
}
