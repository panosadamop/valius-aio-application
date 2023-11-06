package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketAttractivenessFactorsCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketAttractivenessFactorsCategory.class);
        MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory1 = new MarketAttractivenessFactorsCategory();
        marketAttractivenessFactorsCategory1.setId(1L);
        MarketAttractivenessFactorsCategory marketAttractivenessFactorsCategory2 = new MarketAttractivenessFactorsCategory();
        marketAttractivenessFactorsCategory2.setId(marketAttractivenessFactorsCategory1.getId());
        assertThat(marketAttractivenessFactorsCategory1).isEqualTo(marketAttractivenessFactorsCategory2);
        marketAttractivenessFactorsCategory2.setId(2L);
        assertThat(marketAttractivenessFactorsCategory1).isNotEqualTo(marketAttractivenessFactorsCategory2);
        marketAttractivenessFactorsCategory1.setId(null);
        assertThat(marketAttractivenessFactorsCategory1).isNotEqualTo(marketAttractivenessFactorsCategory2);
    }
}
