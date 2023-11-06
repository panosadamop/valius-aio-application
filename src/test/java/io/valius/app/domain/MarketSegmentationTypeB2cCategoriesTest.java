package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketSegmentationTypeB2cCategoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketSegmentationTypeB2cCategories.class);
        MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories1 = new MarketSegmentationTypeB2cCategories();
        marketSegmentationTypeB2cCategories1.setId(1L);
        MarketSegmentationTypeB2cCategories marketSegmentationTypeB2cCategories2 = new MarketSegmentationTypeB2cCategories();
        marketSegmentationTypeB2cCategories2.setId(marketSegmentationTypeB2cCategories1.getId());
        assertThat(marketSegmentationTypeB2cCategories1).isEqualTo(marketSegmentationTypeB2cCategories2);
        marketSegmentationTypeB2cCategories2.setId(2L);
        assertThat(marketSegmentationTypeB2cCategories1).isNotEqualTo(marketSegmentationTypeB2cCategories2);
        marketSegmentationTypeB2cCategories1.setId(null);
        assertThat(marketSegmentationTypeB2cCategories1).isNotEqualTo(marketSegmentationTypeB2cCategories2);
    }
}
