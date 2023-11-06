package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuyingCriteriaWeightingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyingCriteriaWeighting.class);
        BuyingCriteriaWeighting buyingCriteriaWeighting1 = new BuyingCriteriaWeighting();
        buyingCriteriaWeighting1.setId(1L);
        BuyingCriteriaWeighting buyingCriteriaWeighting2 = new BuyingCriteriaWeighting();
        buyingCriteriaWeighting2.setId(buyingCriteriaWeighting1.getId());
        assertThat(buyingCriteriaWeighting1).isEqualTo(buyingCriteriaWeighting2);
        buyingCriteriaWeighting2.setId(2L);
        assertThat(buyingCriteriaWeighting1).isNotEqualTo(buyingCriteriaWeighting2);
        buyingCriteriaWeighting1.setId(null);
        assertThat(buyingCriteriaWeighting1).isNotEqualTo(buyingCriteriaWeighting2);
    }
}
