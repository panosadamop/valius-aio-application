package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldBuyingCriteriaWeightingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldBuyingCriteriaWeighting.class);
        FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting1 = new FieldBuyingCriteriaWeighting();
        fieldBuyingCriteriaWeighting1.setId(1L);
        FieldBuyingCriteriaWeighting fieldBuyingCriteriaWeighting2 = new FieldBuyingCriteriaWeighting();
        fieldBuyingCriteriaWeighting2.setId(fieldBuyingCriteriaWeighting1.getId());
        assertThat(fieldBuyingCriteriaWeighting1).isEqualTo(fieldBuyingCriteriaWeighting2);
        fieldBuyingCriteriaWeighting2.setId(2L);
        assertThat(fieldBuyingCriteriaWeighting1).isNotEqualTo(fieldBuyingCriteriaWeighting2);
        fieldBuyingCriteriaWeighting1.setId(null);
        assertThat(fieldBuyingCriteriaWeighting1).isNotEqualTo(fieldBuyingCriteriaWeighting2);
    }
}
