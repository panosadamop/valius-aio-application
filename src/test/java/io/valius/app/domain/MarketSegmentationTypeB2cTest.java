package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketSegmentationTypeB2cTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketSegmentationTypeB2c.class);
        MarketSegmentationTypeB2c marketSegmentationTypeB2c1 = new MarketSegmentationTypeB2c();
        marketSegmentationTypeB2c1.setId(1L);
        MarketSegmentationTypeB2c marketSegmentationTypeB2c2 = new MarketSegmentationTypeB2c();
        marketSegmentationTypeB2c2.setId(marketSegmentationTypeB2c1.getId());
        assertThat(marketSegmentationTypeB2c1).isEqualTo(marketSegmentationTypeB2c2);
        marketSegmentationTypeB2c2.setId(2L);
        assertThat(marketSegmentationTypeB2c1).isNotEqualTo(marketSegmentationTypeB2c2);
        marketSegmentationTypeB2c1.setId(null);
        assertThat(marketSegmentationTypeB2c1).isNotEqualTo(marketSegmentationTypeB2c2);
    }
}
