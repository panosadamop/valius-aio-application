package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketSegmentationTypeB2bCategoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketSegmentationTypeB2bCategories.class);
        MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories1 = new MarketSegmentationTypeB2bCategories();
        marketSegmentationTypeB2bCategories1.setId(1L);
        MarketSegmentationTypeB2bCategories marketSegmentationTypeB2bCategories2 = new MarketSegmentationTypeB2bCategories();
        marketSegmentationTypeB2bCategories2.setId(marketSegmentationTypeB2bCategories1.getId());
        assertThat(marketSegmentationTypeB2bCategories1).isEqualTo(marketSegmentationTypeB2bCategories2);
        marketSegmentationTypeB2bCategories2.setId(2L);
        assertThat(marketSegmentationTypeB2bCategories1).isNotEqualTo(marketSegmentationTypeB2bCategories2);
        marketSegmentationTypeB2bCategories1.setId(null);
        assertThat(marketSegmentationTypeB2bCategories1).isNotEqualTo(marketSegmentationTypeB2bCategories2);
    }
}
