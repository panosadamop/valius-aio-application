package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldPreferredPurchaseChannelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldPreferredPurchaseChannel.class);
        FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel1 = new FieldPreferredPurchaseChannel();
        fieldPreferredPurchaseChannel1.setId(1L);
        FieldPreferredPurchaseChannel fieldPreferredPurchaseChannel2 = new FieldPreferredPurchaseChannel();
        fieldPreferredPurchaseChannel2.setId(fieldPreferredPurchaseChannel1.getId());
        assertThat(fieldPreferredPurchaseChannel1).isEqualTo(fieldPreferredPurchaseChannel2);
        fieldPreferredPurchaseChannel2.setId(2L);
        assertThat(fieldPreferredPurchaseChannel1).isNotEqualTo(fieldPreferredPurchaseChannel2);
        fieldPreferredPurchaseChannel1.setId(null);
        assertThat(fieldPreferredPurchaseChannel1).isNotEqualTo(fieldPreferredPurchaseChannel2);
    }
}
