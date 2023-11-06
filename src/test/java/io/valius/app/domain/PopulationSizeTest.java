package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PopulationSizeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PopulationSize.class);
        PopulationSize populationSize1 = new PopulationSize();
        populationSize1.setId(1L);
        PopulationSize populationSize2 = new PopulationSize();
        populationSize2.setId(populationSize1.getId());
        assertThat(populationSize1).isEqualTo(populationSize2);
        populationSize2.setId(2L);
        assertThat(populationSize1).isNotEqualTo(populationSize2);
        populationSize1.setId(null);
        assertThat(populationSize1).isNotEqualTo(populationSize2);
    }
}
