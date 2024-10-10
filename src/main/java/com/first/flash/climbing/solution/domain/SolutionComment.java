package com.first.flash.climbing.solution.domain;

import com.first.flash.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class SolutionComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    private CommenterDetail commenterDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;

    protected SolutionComment(final String content, final CommenterDetail commenter, final Solution solution) {
        this.content = content;
        this.commenterDetail = commenter;
        this.solution = solution;
    }

    public static SolutionComment of(final String content, final String commenter, final String profileImage, final UUID commenterId, final Solution solution) {
        return new SolutionComment(content, CommenterDetail.of(commenterId, commenter, profileImage), solution);
    }

    public void updateContent(final String content) {
        this.content = content;
    }
}
