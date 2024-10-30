package cleverton.heusner.adapter.input.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.adapter.input.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.adapter.input.constant.MessageClasspath.SCHEMA_MESSAGES;
import static cleverton.heusner.adapter.input.constant.doc.schema.AuthorSchemaDoc.*;
import static cleverton.heusner.adapter.input.constant.validation.AuthorValidationErrorMessage.*;

@Schema(description = AUTHOR_REGISTER)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public record AuthorCreationRequest(@Schema(
                                        description = AUTHOR_NAME,
                                        minLength = AUTHOR_NAME_MIN_SIZE,
                                        maxLength = AUTHOR_NAME_MAX_SIZE,
                                        example = "Michael Ende"
                                    )
                                    @Size(
                                            min = AUTHOR_NAME_MIN_SIZE,
                                            max = AUTHOR_NAME_MAX_SIZE,
                                            message = SIZE_AUTHOR_NAME
                                    )
                                    String name,

                                    @Schema(description = AUTHOR_BIRTH_DATE, example = "2000-01-01")
                                    LocalDate birthDate) {}