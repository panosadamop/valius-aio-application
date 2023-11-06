package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequiredSampleSizeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequiredSampleSize.class);
        RequiredSampleSize requiredSampleSize1 = new RequiredSampleSize();
        requiredSampleSize1.setId(1L);
        RequiredSampleSize requiredSampleSize2 = new RequiredSampleSize();
        requiredSampleSize2.setId(requiredSampleSize1.getId());
        assertThat(requiredSampleSize1).isEqualTo(requiredSampleSize2);
        requiredSampleSize2.setId(2L);
        assertThat(requiredSampleSize1).isNotEqualTo(requiredSampleSize2);
        requiredSampleSize1.setId(null);
        assertThat(requiredSampleSize1).isNotEqualTo(requiredSampleSize2);
    }
}
