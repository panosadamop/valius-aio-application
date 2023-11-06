package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SegmentUniqueCharacteristicTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SegmentUniqueCharacteristic.class);
        SegmentUniqueCharacteristic segmentUniqueCharacteristic1 = new SegmentUniqueCharacteristic();
        segmentUniqueCharacteristic1.setId(1L);
        SegmentUniqueCharacteristic segmentUniqueCharacteristic2 = new SegmentUniqueCharacteristic();
        segmentUniqueCharacteristic2.setId(segmentUniqueCharacteristic1.getId());
        assertThat(segmentUniqueCharacteristic1).isEqualTo(segmentUniqueCharacteristic2);
        segmentUniqueCharacteristic2.setId(2L);
        assertThat(segmentUniqueCharacteristic1).isNotEqualTo(segmentUniqueCharacteristic2);
        segmentUniqueCharacteristic1.setId(null);
        assertThat(segmentUniqueCharacteristic1).isNotEqualTo(segmentUniqueCharacteristic2);
    }
}
