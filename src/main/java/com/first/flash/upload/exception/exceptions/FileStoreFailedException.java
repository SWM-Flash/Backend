package com.first.flash.upload.exception.exceptions;

public class FileStoreFailedException extends RuntimeException {

    public FileStoreFailedException() {
        super("파일 업로드에 실패했습니다.");
    }
}
