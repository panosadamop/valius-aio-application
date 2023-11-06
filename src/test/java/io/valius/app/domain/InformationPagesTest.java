package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InformationPagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformationPages.class);
        InformationPages informationPages1 = new InformationPages();
        informationPages1.setId(1L);
        InformationPages informationPages2 = new InformationPages();
        informationPages2.setId(informationPages1.getId());
        assertThat(informationPages1).isEqualTo(informationPages2);
        informationPages2.setId(2L);
        assertThat(informationPages1).isNotEqualTo(informationPages2);
        informationPages1.setId(null);
        assertThat(informationPages1).isNotEqualTo(informationPages2);
    }
}
