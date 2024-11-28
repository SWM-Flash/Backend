package com.first.flash.climbing.favorite.domain;

import com.first.flash.global.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class MemberFavoriteGym extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID memberId;
    private Long gymId;

    protected MemberFavoriteGym(final UUID memberId, final Long gymId) {
        this.memberId = memberId;
        this.gymId = gymId;
    }

    public static MemberFavoriteGym createDefault(final UUID memberId, final Long gymId) {
        return new MemberFavoriteGym(memberId, gymId);
    }
}
