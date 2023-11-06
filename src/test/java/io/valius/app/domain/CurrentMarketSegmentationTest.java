package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CurrentMarketSegmentationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrentMarketSegmentation.class);
        CurrentMarketSegmentation currentMarketSegmentation1 = new CurrentMarketSegmentation();
        currentMarketSegmentation1.setId(1L);
        CurrentMarketSegmentation currentMarketSegmentation2 = new CurrentMarketSegmentation();
        currentMarketSegmentation2.setId(currentMarketSegmentation1.getId());
        assertThat(currentMarketSegmentation1).isEqualTo(currentMarketSegmentation2);
        currentMarketSegmentation2.setId(2L);
        assertThat(currentMarketSegmentation1).isNotEqualTo(currentMarketSegmentation2);
        currentMarketSegmentation1.setId(null);
        assertThat(currentMarketSegmentation1).isNotEqualTo(currentMarketSegmentation2);
    }
}
