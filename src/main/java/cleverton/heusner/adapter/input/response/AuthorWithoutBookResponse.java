package cleverton.heusner.adapter.input.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.schema.AuthorSchemaDoc.*;

@Schema(description = AUTHOR_WITHOUT_BOOK)
@Data
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public class AuthorWithoutBookResponse {

    @Schema(description = AUTHOR_ID)
    protected Long id;

    @Schema(description = AUTHOR_NAME)
    protected String name;

    @Schema(description = AUTHOR_BIRTH_DATE)
    protected LocalDate birthDate;

    @Schema(description = AUTHOR_CREATION_DATE)
    protected LocalDateTime createdDate;

    @Schema(description = AUTHOR_MODIFICATION_LAST_DATE)
    protected LocalDateTime lastModifiedDate;
}