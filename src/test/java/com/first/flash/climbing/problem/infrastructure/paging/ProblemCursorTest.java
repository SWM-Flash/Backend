package com.first.flash.climbing.problem.infrastructure.paging;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.first.flash.climbing.problem.exception.exceptions.InvalidCursorException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ProblemCursorTest {

    @Test
    void 커서_인코딩_디코딩() {
        // given
        ProblemSortBy problemSortBy = ProblemSortBy.DIFFICULTY;
        String value = "10";
        UUID lastId = UUID.randomUUID();
        ProblemCursor problemCursor = new ProblemCursor(problemSortBy, value, lastId);

        // when
        String encode = problemCursor.encode();
        ProblemCursor decode = ProblemCursor.decode(encode);

        // then
        assertSoftly(softly->{
            softly.assertThat(decode)
                  .isNotNull();
            softly.assertThat(decode)
                .usingRecursiveComparison()
                .isEqualTo(problemCursor);
        });
    }

    @Test
    void 커서_디코딩_예외() {
        // given
        String invalidCursor = "invalidCursor";

        // when & then
        assertThatThrownBy(() -> ProblemCursor.decode(invalidCursor))
            .isInstanceOf(InvalidCursorException.class);
    }
}
