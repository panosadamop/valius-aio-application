package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldCompanyObjectivesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldCompanyObjectives.class);
        FieldCompanyObjectives fieldCompanyObjectives1 = new FieldCompanyObjectives();
        fieldCompanyObjectives1.setId(1L);
        FieldCompanyObjectives fieldCompanyObjectives2 = new FieldCompanyObjectives();
        fieldCompanyObjectives2.setId(fieldCompanyObjectives1.getId());
        assertThat(fieldCompanyObjectives1).isEqualTo(fieldCompanyObjectives2);
        fieldCompanyObjectives2.setId(2L);
        assertThat(fieldCompanyObjectives1).isNotEqualTo(fieldCompanyObjectives2);
        fieldCompanyObjectives1.setId(null);
        assertThat(fieldCompanyObjectives1).isNotEqualTo(fieldCompanyObjectives2);
    }
}
