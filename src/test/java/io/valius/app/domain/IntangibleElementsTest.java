package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntangibleElementsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntangibleElements.class);
        IntangibleElements intangibleElements1 = new IntangibleElements();
        intangibleElements1.setId(1L);
        IntangibleElements intangibleElements2 = new IntangibleElements();
        intangibleElements2.setId(intangibleElements1.getId());
        assertThat(intangibleElements1).isEqualTo(intangibleElements2);
        intangibleElements2.setId(2L);
        assertThat(intangibleElements1).isNotEqualTo(intangibleElements2);
        intangibleElements1.setId(null);
        assertThat(intangibleElements1).isNotEqualTo(intangibleElements2);
    }
}
