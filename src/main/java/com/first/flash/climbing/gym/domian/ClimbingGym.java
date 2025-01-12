package com.first.flash.climbing.gym.domian;

import com.first.flash.climbing.gym.domian.vo.Difficulty;
import com.first.flash.climbing.gym.exception.exceptions.DifficultyNotFoundException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ClimbingGym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gymName;
    private String thumbnailUrl;
    private String mapImageUrl;
    private String calendarImageUrl;

    @Transient
    private List<Difficulty> difficulties = new ArrayList<>();

    public ClimbingGym(final String gymName, final String thumbnailUrl, final String mapImageUrl,
        final String calendarImageUrl) {
        this.gymName = gymName;
        this.thumbnailUrl = thumbnailUrl;
        this.mapImageUrl = mapImageUrl;
        this.calendarImageUrl = calendarImageUrl;
    }

    public void updateDifficulties(final List<Difficulty> difficulties) {
        this.difficulties = difficulties;
    }

    public Difficulty getDifficultyByName(final String difficultyName) {
        return difficulties.stream()
                           .filter(difficulty -> difficulty.hasSameName(difficultyName))
                           .findAny()
                           .orElseThrow(() -> new DifficultyNotFoundException(difficultyName));
    }
}
