package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompetitivePositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitivePosition.class);
        CompetitivePosition competitivePosition1 = new CompetitivePosition();
        competitivePosition1.setId(1L);
        CompetitivePosition competitivePosition2 = new CompetitivePosition();
        competitivePosition2.setId(competitivePosition1.getId());
        assertThat(competitivePosition1).isEqualTo(competitivePosition2);
        competitivePosition2.setId(2L);
        assertThat(competitivePosition1).isNotEqualTo(competitivePosition2);
        competitivePosition1.setId(null);
        assertThat(competitivePosition1).isNotEqualTo(competitivePosition2);
    }
}
