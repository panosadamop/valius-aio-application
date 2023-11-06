package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StrategicFocusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StrategicFocus.class);
        StrategicFocus strategicFocus1 = new StrategicFocus();
        strategicFocus1.setId(1L);
        StrategicFocus strategicFocus2 = new StrategicFocus();
        strategicFocus2.setId(strategicFocus1.getId());
        assertThat(strategicFocus1).isEqualTo(strategicFocus2);
        strategicFocus2.setId(2L);
        assertThat(strategicFocus1).isNotEqualTo(strategicFocus2);
        strategicFocus1.setId(null);
        assertThat(strategicFocus1).isNotEqualTo(strategicFocus2);
    }
}
