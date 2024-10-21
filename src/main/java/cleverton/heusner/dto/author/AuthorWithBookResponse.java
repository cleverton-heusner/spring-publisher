package cleverton.heusner.dto.author;

import cleverton.heusner.dto.book.BookWithoutAuthorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.PropertySource;

import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;

@Schema(description = "author.with.book")
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@Data
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public class AuthorWithBookResponse extends AuthorWithoutBookResponse {

    @Schema(description = "author.book")
    @JsonProperty("book")
    private BookWithoutAuthorResponse bookWithoutAuthorResponse;
}