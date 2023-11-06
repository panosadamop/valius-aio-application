package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldBuyingCriteriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldBuyingCriteria.class);
        FieldBuyingCriteria fieldBuyingCriteria1 = new FieldBuyingCriteria();
        fieldBuyingCriteria1.setId(1L);
        FieldBuyingCriteria fieldBuyingCriteria2 = new FieldBuyingCriteria();
        fieldBuyingCriteria2.setId(fieldBuyingCriteria1.getId());
        assertThat(fieldBuyingCriteria1).isEqualTo(fieldBuyingCriteria2);
        fieldBuyingCriteria2.setId(2L);
        assertThat(fieldBuyingCriteria1).isNotEqualTo(fieldBuyingCriteria2);
        fieldBuyingCriteria1.setId(null);
        assertThat(fieldBuyingCriteria1).isNotEqualTo(fieldBuyingCriteria2);
    }
}
