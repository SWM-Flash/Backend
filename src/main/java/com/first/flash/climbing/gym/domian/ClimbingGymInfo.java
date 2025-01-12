package com.first.flash.climbing.gym.domian;

import com.first.flash.climbing.gym.domian.vo.Difficulty;
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
public class ClimbingGymInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gymInfoName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "DIFFICULTY",
        joinColumns = @JoinColumn(name = "GYM_NUMBER")
    )
    @OrderColumn(name = "DIFFICULTY_INDEX")
    private List<Difficulty> difficulties;

    public ClimbingGymInfo(final String gymInfoName, final List<Difficulty> difficulties) {
        this.gymInfoName = gymInfoName;
        this.difficulties = difficulties;
    }
}
