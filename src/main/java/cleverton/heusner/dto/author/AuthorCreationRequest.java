package cleverton.heusner.dto.author;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.PropertySource;

import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;

@Schema(description = "author.register")
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public record AuthorCreationRequest(@Schema(description = "author.name", minLength = 1, maxLength = 20,
                                    example = "J.K. Rowling, TOLKIEN")
                                    @Size(min = 1, max = 20, message = "{Size.author.name}")
                                    String name) {}