package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganisationTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisationType.class);
        OrganisationType organisationType1 = new OrganisationType();
        organisationType1.setId(1L);
        OrganisationType organisationType2 = new OrganisationType();
        organisationType2.setId(organisationType1.getId());
        assertThat(organisationType1).isEqualTo(organisationType2);
        organisationType2.setId(2L);
        assertThat(organisationType1).isNotEqualTo(organisationType2);
        organisationType1.setId(null);
        assertThat(organisationType1).isNotEqualTo(organisationType2);
    }
}
