package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EconomicFactorsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EconomicFactors.class);
        EconomicFactors economicFactors1 = new EconomicFactors();
        economicFactors1.setId(1L);
        EconomicFactors economicFactors2 = new EconomicFactors();
        economicFactors2.setId(economicFactors1.getId());
        assertThat(economicFactors1).isEqualTo(economicFactors2);
        economicFactors2.setId(2L);
        assertThat(economicFactors1).isNotEqualTo(economicFactors2);
        economicFactors1.setId(null);
        assertThat(economicFactors1).isNotEqualTo(economicFactors2);
    }
}
