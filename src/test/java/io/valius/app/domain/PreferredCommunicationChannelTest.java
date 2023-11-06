package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PreferredCommunicationChannelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreferredCommunicationChannel.class);
        PreferredCommunicationChannel preferredCommunicationChannel1 = new PreferredCommunicationChannel();
        preferredCommunicationChannel1.setId(1L);
        PreferredCommunicationChannel preferredCommunicationChannel2 = new PreferredCommunicationChannel();
        preferredCommunicationChannel2.setId(preferredCommunicationChannel1.getId());
        assertThat(preferredCommunicationChannel1).isEqualTo(preferredCommunicationChannel2);
        preferredCommunicationChannel2.setId(2L);
        assertThat(preferredCommunicationChannel1).isNotEqualTo(preferredCommunicationChannel2);
        preferredCommunicationChannel1.setId(null);
        assertThat(preferredCommunicationChannel1).isNotEqualTo(preferredCommunicationChannel2);
    }
}
