package cleverton.heusner.service;

import cleverton.heusner.service.message.MessageService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class ServiceConfiguration {

    @Mock
    protected MessageService messageService;

    protected String mockErrorMessage() {
        return messageService.getMessage(anyString(), any(Object[].class));
    }
}