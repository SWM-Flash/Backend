package com.first.flash.climbing.solution.infrastructure;

import static com.first.flash.climbing.solution.domain.QSolutionComment.solutionComment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SolutionCommentQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public void updateCommenterInfo(final UUID commenterId, final String nickName,
        final String profileImageUrl) {
        jpaQueryFactory.update(solutionComment)
            .set(solutionComment.commenterDetail.commenter, nickName)
            .set(solutionComment.commenterDetail.profileImageUrl, profileImageUrl)
            .where(solutionComment.commenterDetail.commenterId.eq(commenterId))
            .execute();
    }
}
