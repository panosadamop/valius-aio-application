package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompetitorCompetitivePositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitorCompetitivePosition.class);
        CompetitorCompetitivePosition competitorCompetitivePosition1 = new CompetitorCompetitivePosition();
        competitorCompetitivePosition1.setId(1L);
        CompetitorCompetitivePosition competitorCompetitivePosition2 = new CompetitorCompetitivePosition();
        competitorCompetitivePosition2.setId(competitorCompetitivePosition1.getId());
        assertThat(competitorCompetitivePosition1).isEqualTo(competitorCompetitivePosition2);
        competitorCompetitivePosition2.setId(2L);
        assertThat(competitorCompetitivePosition1).isNotEqualTo(competitorCompetitivePosition2);
        competitorCompetitivePosition1.setId(null);
        assertThat(competitorCompetitivePosition1).isNotEqualTo(competitorCompetitivePosition2);
    }
}
