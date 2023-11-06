package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreferredPurchaseChannelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferredPurchaseChannel.class);
        PreferredPurchaseChannel preferredPurchaseChannel1 = new PreferredPurchaseChannel();
        preferredPurchaseChannel1.setId(1L);
        PreferredPurchaseChannel preferredPurchaseChannel2 = new PreferredPurchaseChannel();
        preferredPurchaseChannel2.setId(preferredPurchaseChannel1.getId());
        assertThat(preferredPurchaseChannel1).isEqualTo(preferredPurchaseChannel2);
        preferredPurchaseChannel2.setId(2L);
        assertThat(preferredPurchaseChannel1).isNotEqualTo(preferredPurchaseChannel2);
        preferredPurchaseChannel1.setId(null);
        assertThat(preferredPurchaseChannel1).isNotEqualTo(preferredPurchaseChannel2);
    }
}
