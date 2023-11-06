package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketSegmentationTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketSegmentationType.class);
        MarketSegmentationType marketSegmentationType1 = new MarketSegmentationType();
        marketSegmentationType1.setId(1L);
        MarketSegmentationType marketSegmentationType2 = new MarketSegmentationType();
        marketSegmentationType2.setId(marketSegmentationType1.getId());
        assertThat(marketSegmentationType1).isEqualTo(marketSegmentationType2);
        marketSegmentationType2.setId(2L);
        assertThat(marketSegmentationType1).isNotEqualTo(marketSegmentationType2);
        marketSegmentationType1.setId(null);
        assertThat(marketSegmentationType1).isNotEqualTo(marketSegmentationType2);
    }
}
