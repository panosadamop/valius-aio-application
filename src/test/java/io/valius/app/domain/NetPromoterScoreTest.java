package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NetPromoterScoreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetPromoterScore.class);
        NetPromoterScore netPromoterScore1 = new NetPromoterScore();
        netPromoterScore1.setId(1L);
        NetPromoterScore netPromoterScore2 = new NetPromoterScore();
        netPromoterScore2.setId(netPromoterScore1.getId());
        assertThat(netPromoterScore1).isEqualTo(netPromoterScore2);
        netPromoterScore2.setId(2L);
        assertThat(netPromoterScore1).isNotEqualTo(netPromoterScore2);
        netPromoterScore1.setId(null);
        assertThat(netPromoterScore1).isNotEqualTo(netPromoterScore2);
    }
}
