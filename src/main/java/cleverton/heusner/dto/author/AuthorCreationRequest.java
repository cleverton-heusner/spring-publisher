package cleverton.heusner.dto.author;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.PropertySource;

import static cleverton.heusner.constant.documentation.schema.AuthorSchemaDoc.AUTHOR_NAME;
import static cleverton.heusner.constant.documentation.schema.AuthorSchemaDoc.AUTHOR_REGISTER;
import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.constant.message.validation.AuthorMessageValidation.SIZE_AUTHOR_NAME;

@Schema(description = AUTHOR_REGISTER)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public record AuthorCreationRequest(@Schema(description = AUTHOR_NAME, minLength = 1, maxLength = 20,
                                    example = "J.K. Rowling, TOLKIEN")
                                    @Size(min = 1, max = 20, message = SIZE_AUTHOR_NAME)
                                    String name) {}