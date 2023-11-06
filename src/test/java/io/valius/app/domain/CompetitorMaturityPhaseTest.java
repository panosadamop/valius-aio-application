package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompetitorMaturityPhaseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitorMaturityPhase.class);
        CompetitorMaturityPhase competitorMaturityPhase1 = new CompetitorMaturityPhase();
        competitorMaturityPhase1.setId(1L);
        CompetitorMaturityPhase competitorMaturityPhase2 = new CompetitorMaturityPhase();
        competitorMaturityPhase2.setId(competitorMaturityPhase1.getId());
        assertThat(competitorMaturityPhase1).isEqualTo(competitorMaturityPhase2);
        competitorMaturityPhase2.setId(2L);
        assertThat(competitorMaturityPhase1).isNotEqualTo(competitorMaturityPhase2);
        competitorMaturityPhase1.setId(null);
        assertThat(competitorMaturityPhase1).isNotEqualTo(competitorMaturityPhase2);
    }
}
