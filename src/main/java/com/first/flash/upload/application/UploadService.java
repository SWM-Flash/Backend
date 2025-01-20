package com.first.flash.upload.application;

import com.first.flash.upload.application.dto.UploadImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final StorageService storageService;
    private final TranscodingService transcodingService;

    public UploadImageResponseDto uploadImage(final MultipartFile imageFile) {
        String imageUrl = storageService.storageImage(imageFile);
        return new UploadImageResponseDto(imageUrl);
    }

    public String uploadAndTranscodingVideo(final MultipartFile file) {
        String inputStorageUrl = uploadVideo(file);
        return transcodingService.transcodeVideo(inputStorageUrl);
    }

    public String uploadVideo(final MultipartFile videoFile) {
        return storageService.storageVideo(videoFile);
    }
}
