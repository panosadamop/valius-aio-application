package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SegmentScoreMAFTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SegmentScoreMAF.class);
        SegmentScoreMAF segmentScoreMAF1 = new SegmentScoreMAF();
        segmentScoreMAF1.setId(1L);
        SegmentScoreMAF segmentScoreMAF2 = new SegmentScoreMAF();
        segmentScoreMAF2.setId(segmentScoreMAF1.getId());
        assertThat(segmentScoreMAF1).isEqualTo(segmentScoreMAF2);
        segmentScoreMAF2.setId(2L);
        assertThat(segmentScoreMAF1).isNotEqualTo(segmentScoreMAF2);
        segmentScoreMAF1.setId(null);
        assertThat(segmentScoreMAF1).isNotEqualTo(segmentScoreMAF2);
    }
}
