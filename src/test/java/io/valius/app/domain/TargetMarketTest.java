package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TargetMarketTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TargetMarket.class);
        TargetMarket targetMarket1 = new TargetMarket();
        targetMarket1.setId(1L);
        TargetMarket targetMarket2 = new TargetMarket();
        targetMarket2.setId(targetMarket1.getId());
        assertThat(targetMarket1).isEqualTo(targetMarket2);
        targetMarket2.setId(2L);
        assertThat(targetMarket1).isNotEqualTo(targetMarket2);
        targetMarket1.setId(null);
        assertThat(targetMarket1).isNotEqualTo(targetMarket2);
    }
}
