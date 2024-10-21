package cleverton.heusner.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;

import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;

@Schema(description = "book.without.author")
@Data
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public class BookWithoutAuthorResponse {

    @Schema(description = "book.id")
    protected Long id;

    @Schema(description = "book.isbn")
    protected String isbn;

    @Schema(description = "book.title")
    protected String title;

    @Schema(description = "book.creation.date")
    protected LocalDateTime createdDate;

    @Schema(description = "book.modification.last.date")
    protected LocalDateTime lastModifiedDate;
}