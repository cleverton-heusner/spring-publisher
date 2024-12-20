package cleverton.heusner.adapter.input.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.PropertySource;

import static cleverton.heusner.adapter.input.constant.doc.schema.AuthorSchemaDoc.AUTHOR_BOOK;
import static cleverton.heusner.adapter.input.constant.doc.schema.AuthorSchemaDoc.AUTHOR_WITH_BOOK;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;

@Schema(description = AUTHOR_WITH_BOOK)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@Data
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public class AuthorWithBookResponse extends AuthorWithoutBookResponse {

    @Schema(description = AUTHOR_BOOK)
    @JsonProperty("book")
    private BookWithoutAuthorResponse bookWithoutAuthorResponse;
}