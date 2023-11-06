package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MarketSegmentationTypeB2bAltTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketSegmentationTypeB2bAlt.class);
        MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt1 = new MarketSegmentationTypeB2bAlt();
        marketSegmentationTypeB2bAlt1.setId(1L);
        MarketSegmentationTypeB2bAlt marketSegmentationTypeB2bAlt2 = new MarketSegmentationTypeB2bAlt();
        marketSegmentationTypeB2bAlt2.setId(marketSegmentationTypeB2bAlt1.getId());
        assertThat(marketSegmentationTypeB2bAlt1).isEqualTo(marketSegmentationTypeB2bAlt2);
        marketSegmentationTypeB2bAlt2.setId(2L);
        assertThat(marketSegmentationTypeB2bAlt1).isNotEqualTo(marketSegmentationTypeB2bAlt2);
        marketSegmentationTypeB2bAlt1.setId(null);
        assertThat(marketSegmentationTypeB2bAlt1).isNotEqualTo(marketSegmentationTypeB2bAlt2);
    }
}
