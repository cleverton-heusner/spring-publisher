package cleverton.heusner.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;

import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.constant.documentation.schema.BookSchemaDoc.*;

@Schema(description = BOOK_WITHOUT_AUTHOR)
@Data
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public class BookWithoutAuthorResponse {

    @Schema(description = BOOK_ID)
    protected Long id;

    @Schema(description = BOOK_ISBN)
    protected String isbn;

    @Schema(description = BOOK_TITLE)
    protected String title;

    @Schema(description = BOOK_CREATION_DATE)
    protected LocalDateTime createdDate;

    @Schema(description = BOOK_MODIFICATION_LAST_DATE)
    protected LocalDateTime lastModifiedDate;
}