package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketSegmentationTypeB2bTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketSegmentationTypeB2b.class);
        MarketSegmentationTypeB2b marketSegmentationTypeB2b1 = new MarketSegmentationTypeB2b();
        marketSegmentationTypeB2b1.setId(1L);
        MarketSegmentationTypeB2b marketSegmentationTypeB2b2 = new MarketSegmentationTypeB2b();
        marketSegmentationTypeB2b2.setId(marketSegmentationTypeB2b1.getId());
        assertThat(marketSegmentationTypeB2b1).isEqualTo(marketSegmentationTypeB2b2);
        marketSegmentationTypeB2b2.setId(2L);
        assertThat(marketSegmentationTypeB2b1).isNotEqualTo(marketSegmentationTypeB2b2);
        marketSegmentationTypeB2b1.setId(null);
        assertThat(marketSegmentationTypeB2b1).isNotEqualTo(marketSegmentationTypeB2b2);
    }
}
