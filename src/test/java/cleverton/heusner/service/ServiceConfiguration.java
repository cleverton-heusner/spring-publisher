package cleverton.heusner.service;

import cleverton.heusner.port.shared.MessageComponent;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class ServiceConfiguration {

    @Mock
    protected MessageComponent messageComponent;

    protected String mockErrorMessage() {
        return messageComponent.getMessage(anyString(), any(Object[].class));
    }
}