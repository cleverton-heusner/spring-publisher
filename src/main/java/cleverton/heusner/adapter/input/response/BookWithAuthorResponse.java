package cleverton.heusner.adapter.input.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.PropertySource;

import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.doc.schema.BookSchemaDoc.BOOK_AUTHOR;
import static cleverton.heusner.adapter.input.constant.doc.schema.BookSchemaDoc.BOOK_WITH_AUTHOR;

@Schema(description = BOOK_WITH_AUTHOR)
@EqualsAndHashCode(callSuper = true)
@Data
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public class BookWithAuthorResponse extends BookWithoutAuthorResponse {

    @Schema(description = BOOK_AUTHOR)
    @JsonProperty("author")
    private AuthorWithoutBookResponse authorWithoutBookResponse;
}