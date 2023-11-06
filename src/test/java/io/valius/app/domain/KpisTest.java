package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KpisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kpis.class);
        Kpis kpis1 = new Kpis();
        kpis1.setId(1L);
        Kpis kpis2 = new Kpis();
        kpis2.setId(kpis1.getId());
        assertThat(kpis1).isEqualTo(kpis2);
        kpis2.setId(2L);
        assertThat(kpis1).isNotEqualTo(kpis2);
        kpis1.setId(null);
        assertThat(kpis1).isNotEqualTo(kpis2);
    }
}
