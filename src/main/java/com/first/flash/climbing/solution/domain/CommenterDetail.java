package com.first.flash.climbing.solution.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class CommenterDetail {

    @Column(columnDefinition = "BINARY(16)")
    private UUID commenterId;
    private String commenter;
    private String profileImageUrl;

    protected CommenterDetail(final UUID commenterId, final String commenter, final String profileImageUrl) {
        this.commenterId = commenterId;
        this.commenter = commenter;
        this.profileImageUrl = profileImageUrl;
    }

    public static CommenterDetail of(final UUID commenterId, final String commenter, final String profileImageUrl) {
        return new CommenterDetail(commenterId, commenter, profileImageUrl);
    }
}
