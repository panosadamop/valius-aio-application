package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LevelFourTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelFour.class);
        LevelFour levelFour1 = new LevelFour();
        levelFour1.setId(1L);
        LevelFour levelFour2 = new LevelFour();
        levelFour2.setId(levelFour1.getId());
        assertThat(levelFour1).isEqualTo(levelFour2);
        levelFour2.setId(2L);
        assertThat(levelFour1).isNotEqualTo(levelFour2);
        levelFour1.setId(null);
        assertThat(levelFour1).isNotEqualTo(levelFour2);
    }
}
