package cleverton.heusner.adapter.input.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MessageClasspath {

    public static final String MESSAGES = "classpath:messages";
    public static final String VALIDATION_MESSAGES = "classpath:ValidationMessages";
    public static final String SCHEMA_MESSAGES = "classpath:schemas-messages";
    public static final String API_MESSAGES = "classpath:apis-messages";
}