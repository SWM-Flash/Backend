package com.first.flash.climbing.problem.infrastructure.paging;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.first.flash.climbing.problem.exception.exceptions.InvalidCursorException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CursorTest {

    @Test
    void 커서_인코딩_디코딩() {
        // given
        SortBy sortBy = SortBy.DIFFICULTY;
        String value = "10";
        UUID lastId = UUID.randomUUID();
        Cursor cursor = new Cursor(sortBy, value, lastId);

        // when
        String encode = cursor.encode();
        Cursor decode = Cursor.decode(encode);

        // then
        assertSoftly(softly->{
            softly.assertThat(decode)
                  .isNotNull();
            softly.assertThat(decode)
                .usingRecursiveComparison()
                .isEqualTo(cursor);
        });
    }

    @Test
    void 커서_디코딩_예외() {
        // given
        String invalidCursor = "invalidCursor";

        // when & then
        assertThatThrownBy(() -> Cursor.decode(invalidCursor))
            .isInstanceOf(InvalidCursorException.class);
    }
}
