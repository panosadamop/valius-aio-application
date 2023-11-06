package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaturityPhaseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaturityPhase.class);
        MaturityPhase maturityPhase1 = new MaturityPhase();
        maturityPhase1.setId(1L);
        MaturityPhase maturityPhase2 = new MaturityPhase();
        maturityPhase2.setId(maturityPhase1.getId());
        assertThat(maturityPhase1).isEqualTo(maturityPhase2);
        maturityPhase2.setId(2L);
        assertThat(maturityPhase1).isNotEqualTo(maturityPhase2);
        maturityPhase1.setId(null);
        assertThat(maturityPhase1).isNotEqualTo(maturityPhase2);
    }
}
