package cleverton.heusner.dto.author;

import cleverton.heusner.validation.age.AgeOfMajority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.constant.documentation.schema.AuthorSchemaDoc.*;
import static cleverton.heusner.constant.message.validation.AuthorMessageValidation.*;

@Schema(description = AUTHOR_REGISTER)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public record AuthorCreationRequest(@Schema(
                                        description = AUTHOR_NAME,
                                        minLength = AUTHOR_NAME_MIN_SIZE,
                                        maxLength = AUTHOR_NAME_MAX_SIZE,
                                        example = "J.K. Rowling"
                                    )
                                    @Size(
                                            min = AUTHOR_NAME_MIN_SIZE,
                                            max = AUTHOR_NAME_MAX_SIZE,
                                            message = SIZE_AUTHOR_NAME
                                    )
                                    String name,

                                    @Schema(description = AUTHOR_BIRTH_DATE, example = "2000-01-01")
                                    @AgeOfMajority
                                    LocalDate birthDate) {}