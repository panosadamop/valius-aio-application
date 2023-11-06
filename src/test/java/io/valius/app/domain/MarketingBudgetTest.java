package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketingBudgetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketingBudget.class);
        MarketingBudget marketingBudget1 = new MarketingBudget();
        marketingBudget1.setId(1L);
        MarketingBudget marketingBudget2 = new MarketingBudget();
        marketingBudget2.setId(marketingBudget1.getId());
        assertThat(marketingBudget1).isEqualTo(marketingBudget2);
        marketingBudget2.setId(2L);
        assertThat(marketingBudget1).isNotEqualTo(marketingBudget2);
        marketingBudget1.setId(null);
        assertThat(marketingBudget1).isNotEqualTo(marketingBudget2);
    }
}
