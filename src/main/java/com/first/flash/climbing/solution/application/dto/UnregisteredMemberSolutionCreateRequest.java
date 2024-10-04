package com.first.flash.climbing.solution.application.dto;

import jakarta.validation.constraints.NotEmpty;

public record UnregisteredMemberSolutionCreateRequest(
    @NotEmpty(message = "닉네임은 필수입니다.") String nickName,
    @NotEmpty(message = "인스타그램 아이디는 필수입니다.") String instagramId,
    String review, String profileImageUrl,
    @NotEmpty(message = "비디오 URL은 필수입니다.") String videoUrl) {

}
