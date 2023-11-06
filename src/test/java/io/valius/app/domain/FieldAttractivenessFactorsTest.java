package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldAttractivenessFactorsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldAttractivenessFactors.class);
        FieldAttractivenessFactors fieldAttractivenessFactors1 = new FieldAttractivenessFactors();
        fieldAttractivenessFactors1.setId(1L);
        FieldAttractivenessFactors fieldAttractivenessFactors2 = new FieldAttractivenessFactors();
        fieldAttractivenessFactors2.setId(fieldAttractivenessFactors1.getId());
        assertThat(fieldAttractivenessFactors1).isEqualTo(fieldAttractivenessFactors2);
        fieldAttractivenessFactors2.setId(2L);
        assertThat(fieldAttractivenessFactors1).isNotEqualTo(fieldAttractivenessFactors2);
        fieldAttractivenessFactors1.setId(null);
        assertThat(fieldAttractivenessFactors1).isNotEqualTo(fieldAttractivenessFactors2);
    }
}
