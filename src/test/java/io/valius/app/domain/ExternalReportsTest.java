package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExternalReportsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExternalReports.class);
        ExternalReports externalReports1 = new ExternalReports();
        externalReports1.setId(1L);
        ExternalReports externalReports2 = new ExternalReports();
        externalReports2.setId(externalReports1.getId());
        assertThat(externalReports1).isEqualTo(externalReports2);
        externalReports2.setId(2L);
        assertThat(externalReports1).isNotEqualTo(externalReports2);
        externalReports1.setId(null);
        assertThat(externalReports1).isNotEqualTo(externalReports2);
    }
}
