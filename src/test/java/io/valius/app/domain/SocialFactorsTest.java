package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocialFactorsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialFactors.class);
        SocialFactors socialFactors1 = new SocialFactors();
        socialFactors1.setId(1L);
        SocialFactors socialFactors2 = new SocialFactors();
        socialFactors2.setId(socialFactors1.getId());
        assertThat(socialFactors1).isEqualTo(socialFactors2);
        socialFactors2.setId(2L);
        assertThat(socialFactors1).isNotEqualTo(socialFactors2);
        socialFactors1.setId(null);
        assertThat(socialFactors1).isNotEqualTo(socialFactors2);
    }
}
