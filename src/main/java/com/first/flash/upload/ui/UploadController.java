package com.first.flash.upload.ui;

import com.first.flash.upload.application.UploadService;
import com.first.flash.upload.application.dto.UploadImageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @Operation(summary = "이미지 업로드", description = "이미지 업로드")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이미지가 성공적으로 업로드됨",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UploadImageResponseDto.class))),
        @ApiResponse(responseCode = "500", description = "이미지 저장 실패")
    })
    @PostMapping("/upload/images")
    public ResponseEntity<UploadImageResponseDto> uploadImage(
        @RequestParam("image") final MultipartFile file) {
        return ResponseEntity.ok(uploadService.uploadImage(file));
    }
}
