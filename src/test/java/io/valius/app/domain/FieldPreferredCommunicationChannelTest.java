package io.valius.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.valius.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldPreferredCommunicationChannelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldPreferredCommunicationChannel.class);
        FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel1 = new FieldPreferredCommunicationChannel();
        fieldPreferredCommunicationChannel1.setId(1L);
        FieldPreferredCommunicationChannel fieldPreferredCommunicationChannel2 = new FieldPreferredCommunicationChannel();
        fieldPreferredCommunicationChannel2.setId(fieldPreferredCommunicationChannel1.getId());
        assertThat(fieldPreferredCommunicationChannel1).isEqualTo(fieldPreferredCommunicationChannel2);
        fieldPreferredCommunicationChannel2.setId(2L);
        assertThat(fieldPreferredCommunicationChannel1).isNotEqualTo(fieldPreferredCommunicationChannel2);
        fieldPreferredCommunicationChannel1.setId(null);
        assertThat(fieldPreferredCommunicationChannel1).isNotEqualTo(fieldPreferredCommunicationChannel2);
    }
}
