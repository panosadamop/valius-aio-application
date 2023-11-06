package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RevenuesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Revenues.class);
        Revenues revenues1 = new Revenues();
        revenues1.setId(1L);
        Revenues revenues2 = new Revenues();
        revenues2.setId(revenues1.getId());
        assertThat(revenues1).isEqualTo(revenues2);
        revenues2.setId(2L);
        assertThat(revenues1).isNotEqualTo(revenues2);
        revenues1.setId(null);
        assertThat(revenues1).isNotEqualTo(revenues2);
    }
}
