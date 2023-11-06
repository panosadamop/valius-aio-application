package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LevelOneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelOne.class);
        LevelOne levelOne1 = new LevelOne();
        levelOne1.setId(1L);
        LevelOne levelOne2 = new LevelOne();
        levelOne2.setId(levelOne1.getId());
        assertThat(levelOne1).isEqualTo(levelOne2);
        levelOne2.setId(2L);
        assertThat(levelOne1).isNotEqualTo(levelOne2);
        levelOne1.setId(null);
        assertThat(levelOne1).isNotEqualTo(levelOne2);
    }
}
