package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfidenceLevelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfidenceLevel.class);
        ConfidenceLevel confidenceLevel1 = new ConfidenceLevel();
        confidenceLevel1.setId(1L);
        ConfidenceLevel confidenceLevel2 = new ConfidenceLevel();
        confidenceLevel2.setId(confidenceLevel1.getId());
        assertThat(confidenceLevel1).isEqualTo(confidenceLevel2);
        confidenceLevel2.setId(2L);
        assertThat(confidenceLevel1).isNotEqualTo(confidenceLevel2);
        confidenceLevel1.setId(null);
        assertThat(confidenceLevel1).isNotEqualTo(confidenceLevel2);
    }
}
