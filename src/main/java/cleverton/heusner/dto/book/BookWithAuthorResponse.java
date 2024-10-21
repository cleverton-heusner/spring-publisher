package cleverton.heusner.dto.book;

import cleverton.heusner.dto.author.AuthorWithoutBookResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.PropertySource;

import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;

@Schema(description = "book.with.author")
@EqualsAndHashCode(callSuper = true)
@Data
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public class BookWithAuthorResponse extends BookWithoutAuthorResponse {

    @Schema(description = "book.author")
    @JsonProperty("author")
    private AuthorWithoutBookResponse authorWithoutBookResponse;
}