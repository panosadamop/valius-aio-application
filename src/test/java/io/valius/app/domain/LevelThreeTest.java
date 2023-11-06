package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LevelThreeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelThree.class);
        LevelThree levelThree1 = new LevelThree();
        levelThree1.setId(1L);
        LevelThree levelThree2 = new LevelThree();
        levelThree2.setId(levelThree1.getId());
        assertThat(levelThree1).isEqualTo(levelThree2);
        levelThree2.setId(2L);
        assertThat(levelThree1).isNotEqualTo(levelThree2);
        levelThree1.setId(null);
        assertThat(levelThree1).isNotEqualTo(levelThree2);
    }
}
