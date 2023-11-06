package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldProductypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldProductype.class);
        FieldProductype fieldProductype1 = new FieldProductype();
        fieldProductype1.setId(1L);
        FieldProductype fieldProductype2 = new FieldProductype();
        fieldProductype2.setId(fieldProductype1.getId());
        assertThat(fieldProductype1).isEqualTo(fieldProductype2);
        fieldProductype2.setId(2L);
        assertThat(fieldProductype1).isNotEqualTo(fieldProductype2);
        fieldProductype1.setId(null);
        assertThat(fieldProductype1).isNotEqualTo(fieldProductype2);
    }
}
