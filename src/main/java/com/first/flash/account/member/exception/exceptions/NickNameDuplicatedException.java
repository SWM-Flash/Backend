package com.first.flash.account.member.exception.exceptions;

public class NickNameDuplicatedException extends RuntimeException {

        public NickNameDuplicatedException() {
            super("닉네임이 중복되었습니다!");
        }
}
