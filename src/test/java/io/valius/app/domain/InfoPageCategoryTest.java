package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfoPageCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoPageCategory.class);
        InfoPageCategory infoPageCategory1 = new InfoPageCategory();
        infoPageCategory1.setId(1L);
        InfoPageCategory infoPageCategory2 = new InfoPageCategory();
        infoPageCategory2.setId(infoPageCategory1.getId());
        assertThat(infoPageCategory1).isEqualTo(infoPageCategory2);
        infoPageCategory2.setId(2L);
        assertThat(infoPageCategory1).isNotEqualTo(infoPageCategory2);
        infoPageCategory1.setId(null);
        assertThat(infoPageCategory1).isNotEqualTo(infoPageCategory2);
    }
}
