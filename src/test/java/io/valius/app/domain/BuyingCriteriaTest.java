package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuyingCriteriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyingCriteria.class);
        BuyingCriteria buyingCriteria1 = new BuyingCriteria();
        buyingCriteria1.setId(1L);
        BuyingCriteria buyingCriteria2 = new BuyingCriteria();
        buyingCriteria2.setId(buyingCriteria1.getId());
        assertThat(buyingCriteria1).isEqualTo(buyingCriteria2);
        buyingCriteria2.setId(2L);
        assertThat(buyingCriteria1).isNotEqualTo(buyingCriteria2);
        buyingCriteria1.setId(null);
        assertThat(buyingCriteria1).isNotEqualTo(buyingCriteria2);
    }
}
