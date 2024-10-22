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
import static cleverton.heusner.constant.message.validation.AuthorMessageValidation.SIZE_AUTHOR_NAME;
import static cleverton.heusner.constant.message.validation.BookMessageValidation.SIZE_BOOK_TITLE;

@Schema(description = BOOK_REGISTER)
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public record BookCreationRequest(@Schema(description = BOOK_ISBN, minLength = 13, maxLength = 13,
                                  example = "9780306406157")
                                  @Isbn13
                                  String isbn,

                                  @Schema(description = BOOK_TITLE, minLength = 1, maxLength = 30, example = "The " +
                                          "Little Prince, THE NEVERENDING STORY, charlie and the chocolate factory")
                                  @Size(min = 1, max = 30, message = SIZE_BOOK_TITLE)
                                  String title,

                                  @Schema(description = AUTHOR_NAME, minLength = 1, maxLength = 20, example = "J.K. " +
                                          "Rowling, TOLKIEN")
                                  @NotBlank(message = SIZE_AUTHOR_NAME)
                                  String authorName) {}