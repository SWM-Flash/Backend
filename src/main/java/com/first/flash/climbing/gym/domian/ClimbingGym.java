package com.first.flash.climbing.gym.domian;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ClimbingGym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gymName;
    private String thumbnailUrl;
    private String mapImageUrl;

    public ClimbingGym(final String gymName, final String thumbnailUrl, final String mapImageUrl) {
        this.gymName = gymName;
        this.thumbnailUrl = thumbnailUrl;
        this.mapImageUrl = mapImageUrl;
    }
}
