package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketSegmentationTypeB2cAltTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketSegmentationTypeB2cAlt.class);
        MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt1 = new MarketSegmentationTypeB2cAlt();
        marketSegmentationTypeB2cAlt1.setId(1L);
        MarketSegmentationTypeB2cAlt marketSegmentationTypeB2cAlt2 = new MarketSegmentationTypeB2cAlt();
        marketSegmentationTypeB2cAlt2.setId(marketSegmentationTypeB2cAlt1.getId());
        assertThat(marketSegmentationTypeB2cAlt1).isEqualTo(marketSegmentationTypeB2cAlt2);
        marketSegmentationTypeB2cAlt2.setId(2L);
        assertThat(marketSegmentationTypeB2cAlt1).isNotEqualTo(marketSegmentationTypeB2cAlt2);
        marketSegmentationTypeB2cAlt1.setId(null);
        assertThat(marketSegmentationTypeB2cAlt1).isNotEqualTo(marketSegmentationTypeB2cAlt2);
    }
}
