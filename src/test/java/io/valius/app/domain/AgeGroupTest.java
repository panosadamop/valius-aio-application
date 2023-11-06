package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgeGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgeGroup.class);
        AgeGroup ageGroup1 = new AgeGroup();
        ageGroup1.setId(1L);
        AgeGroup ageGroup2 = new AgeGroup();
        ageGroup2.setId(ageGroup1.getId());
        assertThat(ageGroup1).isEqualTo(ageGroup2);
        ageGroup2.setId(2L);
        assertThat(ageGroup1).isNotEqualTo(ageGroup2);
        ageGroup1.setId(null);
        assertThat(ageGroup1).isNotEqualTo(ageGroup2);
    }
}
