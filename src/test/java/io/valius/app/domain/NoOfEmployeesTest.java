package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoOfEmployeesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoOfEmployees.class);
        NoOfEmployees noOfEmployees1 = new NoOfEmployees();
        noOfEmployees1.setId(1L);
        NoOfEmployees noOfEmployees2 = new NoOfEmployees();
        noOfEmployees2.setId(noOfEmployees1.getId());
        assertThat(noOfEmployees1).isEqualTo(noOfEmployees2);
        noOfEmployees2.setId(2L);
        assertThat(noOfEmployees1).isNotEqualTo(noOfEmployees2);
        noOfEmployees1.setId(null);
        assertThat(noOfEmployees1).isNotEqualTo(noOfEmployees2);
    }
}
