package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LevelTwoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelTwo.class);
        LevelTwo levelTwo1 = new LevelTwo();
        levelTwo1.setId(1L);
        LevelTwo levelTwo2 = new LevelTwo();
        levelTwo2.setId(levelTwo1.getId());
        assertThat(levelTwo1).isEqualTo(levelTwo2);
        levelTwo2.setId(2L);
        assertThat(levelTwo1).isNotEqualTo(levelTwo2);
        levelTwo1.setId(null);
        assertThat(levelTwo1).isNotEqualTo(levelTwo2);
    }
}
