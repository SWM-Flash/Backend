package com.first.flash.climbing.gym.domian;

import com.first.flash.climbing.gym.domian.vo.Difficulty;
import com.first.flash.climbing.gym.exception.exceptions.DifficultyNotFoundException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "DIFFICULTY",
        joinColumns = @JoinColumn(name = "GYM_NUMBER")
    )
    @OrderColumn(name = "DIFFICULTY_INDEX")
    private List<Difficulty> difficulties;

    public ClimbingGym(final String gymName, final String thumbnailUrl, final String mapImageUrl,
        final List<Difficulty> difficulties) {
        this.gymName = gymName;
        this.thumbnailUrl = thumbnailUrl;
        this.mapImageUrl = mapImageUrl;
        this.difficulties = difficulties;
    }

    public Difficulty getDifficultyByName(final String difficultyName) {
        return difficulties.stream()
            .filter(difficulty -> difficulty.hasSameName(difficultyName))
            .findAny()
            .orElseThrow(() -> new DifficultyNotFoundException(difficultyName));
    }
}
