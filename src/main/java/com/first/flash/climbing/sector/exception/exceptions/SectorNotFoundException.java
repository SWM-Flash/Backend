package com.first.flash.climbing.sector.exception.exceptions;

public class SectorNotFoundException extends RuntimeException {

    public SectorNotFoundException(final Long id) {
        super(String.format("아이디가 %s인 섹터를 찾을 수 없습니다.", id));
    }
}
