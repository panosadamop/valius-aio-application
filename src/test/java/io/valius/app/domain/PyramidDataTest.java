package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PyramidDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PyramidData.class);
        PyramidData pyramidData1 = new PyramidData();
        pyramidData1.setId(1L);
        PyramidData pyramidData2 = new PyramidData();
        pyramidData2.setId(pyramidData1.getId());
        assertThat(pyramidData1).isEqualTo(pyramidData2);
        pyramidData2.setId(2L);
        assertThat(pyramidData1).isNotEqualTo(pyramidData2);
        pyramidData1.setId(null);
        assertThat(pyramidData1).isNotEqualTo(pyramidData2);
    }
}
