package com.first.flash.climbing.solution.application.dto;

import com.first.flash.climbing.solution.domain.PerceivedDifficulty;
import com.first.flash.global.annotation.ValidEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UnregisteredMemberSolutionCreateRequest(
    @NotEmpty(message = "닉네임은 필수입니다.") String nickName,
    @NotEmpty(message = "인스타그램 아이디는 필수입니다.") String instagramId,
    String review, String profileImageUrl,
    @NotEmpty(message = "비디오 URL은 필수입니다.") String videoUrl,
    @NotNull(message = "체감 난이도는 필수입니다.")
    @ValidEnum(enumClass = PerceivedDifficulty.class) PerceivedDifficulty perceivedDifficulty) {

}
