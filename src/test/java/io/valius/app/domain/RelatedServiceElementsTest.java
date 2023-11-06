package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatedServiceElementsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelatedServiceElements.class);
        RelatedServiceElements relatedServiceElements1 = new RelatedServiceElements();
        relatedServiceElements1.setId(1L);
        RelatedServiceElements relatedServiceElements2 = new RelatedServiceElements();
        relatedServiceElements2.setId(relatedServiceElements1.getId());
        assertThat(relatedServiceElements1).isEqualTo(relatedServiceElements2);
        relatedServiceElements2.setId(2L);
        assertThat(relatedServiceElements1).isNotEqualTo(relatedServiceElements2);
        relatedServiceElements1.setId(null);
        assertThat(relatedServiceElements1).isNotEqualTo(relatedServiceElements2);
    }
}
