package cleverton.heusner.dto.author;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;

import static cleverton.heusner.constant.documentation.schema.AuthorSchemaDoc.*;
import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;

@Schema(description = AUTHOR_WITHOUT_BOOK)
@Data
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public class AuthorWithoutBookResponse {

    @Schema(description = AUTHOR_ID)
    protected Long id;

    @Schema(description = AUTHOR_NAME)
    protected String name;

    @Schema(description = AUTHOR_CREATION_DATE)
    protected LocalDateTime createdDate;

    @Schema(description = AUTHOR_MODIFICATION_LAST_DATE)
    protected LocalDateTime lastModifiedDate;
}