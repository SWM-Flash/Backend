package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.PerceivedDifficulty;
import com.first.flash.global.annotation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

public record UnregisteredMemberSolutionCreateRequest(
    MultipartFile solutionVideo,
    @NotEmpty(message = "닉네임은 필수입니다.") String nickName,
    @NotEmpty(message = "인스타그램 아이디는 필수입니다.") String instagramId,
    String review, String profileImageUrl,
    @NotEmpty(message = "썸네일 URL은 필수입니다.") String thumbnailImageUrl,
    @NotNull(message = "풀이 일자 정보는 필수입니다.") LocalDate solvedDate,
    @NotNull(message = "체감 난이도는 필수입니다.")
    @ValidEnum(enumClass = PerceivedDifficulty.class) PerceivedDifficulty perceivedDifficulty) {

}
