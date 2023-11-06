package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CoreProductElementsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoreProductElements.class);
        CoreProductElements coreProductElements1 = new CoreProductElements();
        coreProductElements1.setId(1L);
        CoreProductElements coreProductElements2 = new CoreProductElements();
        coreProductElements2.setId(coreProductElements1.getId());
        assertThat(coreProductElements1).isEqualTo(coreProductElements2);
        coreProductElements2.setId(2L);
        assertThat(coreProductElements1).isNotEqualTo(coreProductElements2);
        coreProductElements1.setId(null);
        assertThat(coreProductElements1).isNotEqualTo(coreProductElements2);
    }
}
