package cleverton.heusner.dto.book;

import cleverton.heusner.validation.isbn.Isbn13;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.PropertySource;

import static cleverton.heusner.configuration.message.MessageBasename.SCHEMA_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;

@Schema(description = "book.register")
@PropertySource(value = SCHEMA_MESSAGES + FILE_FORMAT, encoding = ENCODING)
public record BookCreationRequest(@Schema(description = "book.isbn", minLength = 13, maxLength = 13,
                                  example = "9780306406157")
                                  @Isbn13
                                  String isbn,

                                  @Schema(description = "book.title", minLength = 1, maxLength = 30, example = "The " +
                                          "Little Prince, THE NEVERENDING STORY, charlie and the chocolate factory")
                                  @Size(min = 1, max = 30, message = "{Size.book.title}")
                                  String title,

                                  @Schema(description = "author.name", minLength = 1, maxLength = 20, example = "J.K. " +
                                          "Rowling, TOLKIEN")
                                  @NotBlank(message = "{Size.author.name}")
                                  String authorName) {}