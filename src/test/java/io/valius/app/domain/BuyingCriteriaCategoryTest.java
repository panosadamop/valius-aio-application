package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuyingCriteriaCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyingCriteriaCategory.class);
        BuyingCriteriaCategory buyingCriteriaCategory1 = new BuyingCriteriaCategory();
        buyingCriteriaCategory1.setId(1L);
        BuyingCriteriaCategory buyingCriteriaCategory2 = new BuyingCriteriaCategory();
        buyingCriteriaCategory2.setId(buyingCriteriaCategory1.getId());
        assertThat(buyingCriteriaCategory1).isEqualTo(buyingCriteriaCategory2);
        buyingCriteriaCategory2.setId(2L);
        assertThat(buyingCriteriaCategory1).isNotEqualTo(buyingCriteriaCategory2);
        buyingCriteriaCategory1.setId(null);
        assertThat(buyingCriteriaCategory1).isNotEqualTo(buyingCriteriaCategory2);
    }
}
