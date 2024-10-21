package cleverton.heusner.dto.author;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;

import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;

@Schema(description = "author.without.book")
@Data
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public class AuthorWithoutBookResponse {

    @Schema(description = "author.id")
    protected Long id;

    @Schema(description = "author.name")
    protected String name;

    @Schema(description = "author.creation.date")
    protected LocalDateTime createdDate;

    @Schema(description = "author.modification.last.date")
    protected LocalDateTime lastModifiedDate;
}