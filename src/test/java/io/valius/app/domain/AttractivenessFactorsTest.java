package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttractivenessFactorsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttractivenessFactors.class);
        AttractivenessFactors attractivenessFactors1 = new AttractivenessFactors();
        attractivenessFactors1.setId(1L);
        AttractivenessFactors attractivenessFactors2 = new AttractivenessFactors();
        attractivenessFactors2.setId(attractivenessFactors1.getId());
        assertThat(attractivenessFactors1).isEqualTo(attractivenessFactors2);
        attractivenessFactors2.setId(2L);
        assertThat(attractivenessFactors1).isNotEqualTo(attractivenessFactors2);
        attractivenessFactors1.setId(null);
        assertThat(attractivenessFactors1).isNotEqualTo(attractivenessFactors2);
    }
}
