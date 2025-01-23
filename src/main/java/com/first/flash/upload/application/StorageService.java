package com.first.flash.upload.application;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String storageImage(final MultipartFile imageFile);

    String storageVideo(final MultipartFile videoFile);
}
