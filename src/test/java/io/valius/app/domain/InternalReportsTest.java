package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InternalReportsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternalReports.class);
        InternalReports internalReports1 = new InternalReports();
        internalReports1.setId(1L);
        InternalReports internalReports2 = new InternalReports();
        internalReports2.setId(internalReports1.getId());
        assertThat(internalReports1).isEqualTo(internalReports2);
        internalReports2.setId(2L);
        assertThat(internalReports1).isNotEqualTo(internalReports2);
        internalReports1.setId(null);
        assertThat(internalReports1).isNotEqualTo(internalReports2);
    }
}
