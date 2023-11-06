package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompetitiveFactorsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitiveFactors.class);
        CompetitiveFactors competitiveFactors1 = new CompetitiveFactors();
        competitiveFactors1.setId(1L);
        CompetitiveFactors competitiveFactors2 = new CompetitiveFactors();
        competitiveFactors2.setId(competitiveFactors1.getId());
        assertThat(competitiveFactors1).isEqualTo(competitiveFactors2);
        competitiveFactors2.setId(2L);
        assertThat(competitiveFactors1).isNotEqualTo(competitiveFactors2);
        competitiveFactors1.setId(null);
        assertThat(competitiveFactors1).isNotEqualTo(competitiveFactors2);
    }
}
