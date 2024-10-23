package cleverton.heusner.dto.book;

import cleverton.heusner.validation.isbn.Isbn13;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.PropertySource;

import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.constant.documentation.schema.AuthorSchemaDoc.AUTHOR_NAME;
import static cleverton.heusner.constant.documentation.schema.BookSchemaDoc.*;
import static cleverton.heusner.constant.message.validation.AuthorMessageValidation.*;
import static cleverton.heusner.constant.message.validation.BookMessageValidation.*;

@Schema(description = BOOK_REGISTER)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public record BookCreationRequest(@Schema(
                                      description = BOOK_ISBN,
                                      minLength = BOOK_ISBN_SIZE,
                                      maxLength = BOOK_ISBN_SIZE,
                                      example = "9780306406157"
                                  )
                                  @Isbn13
                                  String isbn,

                                  @Schema(
                                          description = BOOK_TITLE,
                                          minLength = BOOK_TITLE_MIN_SIZE,
                                          maxLength = BOOK_TITLE_MAX_SIZE,
                                          example = "The NeverEnding Story"
                                  )
                                  @Size(
                                          min = BOOK_TITLE_MIN_SIZE,
                                          max = BOOK_TITLE_MAX_SIZE,
                                          message = SIZE_BOOK_TITLE
                                  )
                                  String title,

                                  @Schema(
                                          description = AUTHOR_NAME,
                                          minLength = AUTHOR_NAME_MIN_SIZE,
                                          maxLength = AUTHOR_NAME_MAX_SIZE,
                                          example = "Michael Ende"
                                  )
                                  @NotBlank(message = SIZE_AUTHOR_NAME)
                                  String authorName) {}