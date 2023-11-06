package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OccupationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Occupation.class);
        Occupation occupation1 = new Occupation();
        occupation1.setId(1L);
        Occupation occupation2 = new Occupation();
        occupation2.setId(occupation1.getId());
        assertThat(occupation1).isEqualTo(occupation2);
        occupation2.setId(2L);
        assertThat(occupation1).isNotEqualTo(occupation2);
        occupation1.setId(null);
        assertThat(occupation1).isNotEqualTo(occupation2);
    }
}
