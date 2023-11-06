package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyObjectivesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyObjectives.class);
        CompanyObjectives companyObjectives1 = new CompanyObjectives();
        companyObjectives1.setId(1L);
        CompanyObjectives companyObjectives2 = new CompanyObjectives();
        companyObjectives2.setId(companyObjectives1.getId());
        assertThat(companyObjectives1).isEqualTo(companyObjectives2);
        companyObjectives2.setId(2L);
        assertThat(companyObjectives1).isNotEqualTo(companyObjectives2);
        companyObjectives1.setId(null);
        assertThat(companyObjectives1).isNotEqualTo(companyObjectives2);
    }
}
