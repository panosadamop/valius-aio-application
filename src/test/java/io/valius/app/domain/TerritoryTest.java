package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TerritoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Territory.class);
        Territory territory1 = new Territory();
        territory1.setId(1L);
        Territory territory2 = new Territory();
        territory2.setId(territory1.getId());
        assertThat(territory1).isEqualTo(territory2);
        territory2.setId(2L);
        assertThat(territory1).isNotEqualTo(territory2);
        territory1.setId(null);
        assertThat(territory1).isNotEqualTo(territory2);
    }
}
