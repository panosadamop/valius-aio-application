package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatisticalErrorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatisticalError.class);
        StatisticalError statisticalError1 = new StatisticalError();
        statisticalError1.setId(1L);
        StatisticalError statisticalError2 = new StatisticalError();
        statisticalError2.setId(statisticalError1.getId());
        assertThat(statisticalError1).isEqualTo(statisticalError2);
        statisticalError2.setId(2L);
        assertThat(statisticalError1).isNotEqualTo(statisticalError2);
        statisticalError1.setId(null);
        assertThat(statisticalError1).isNotEqualTo(statisticalError2);
    }
}
