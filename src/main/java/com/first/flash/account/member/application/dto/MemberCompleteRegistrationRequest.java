package com.first.flash.account.member.application.dto;

public record MemberCompleteRegistrationRequest(String nickName, String instagramId, Double height,
                                                Double reach, String profileImageUrl) {

}
