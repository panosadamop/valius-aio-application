package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldKpiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldKpi.class);
        FieldKpi fieldKpi1 = new FieldKpi();
        fieldKpi1.setId(1L);
        FieldKpi fieldKpi2 = new FieldKpi();
        fieldKpi2.setId(fieldKpi1.getId());
        assertThat(fieldKpi1).isEqualTo(fieldKpi2);
        fieldKpi2.setId(2L);
        assertThat(fieldKpi1).isNotEqualTo(fieldKpi2);
        fieldKpi1.setId(null);
        assertThat(fieldKpi1).isNotEqualTo(fieldKpi2);
    }
}
