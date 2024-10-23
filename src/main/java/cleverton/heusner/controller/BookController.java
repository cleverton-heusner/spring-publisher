package cleverton.heusner.controller;

import cleverton.heusner.dto.book.BookCreationRequest;
import cleverton.heusner.dto.book.BookWithAuthorResponse;
import cleverton.heusner.mapper.book.BookCreationRequestMapper;
import cleverton.heusner.mapper.book.BookResponseMapper;
import cleverton.heusner.service.book.BookService;
import cleverton.heusner.validation.id.Id;
import cleverton.heusner.validation.isbn.Isbn13;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cleverton.heusner.configuration.message.MessageBasename.API_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.constant.documentation.api.BookApiDoc.*;
import static cleverton.heusner.constant.api.HttpStatusCode.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(
        name= BOOK_TAG_NAME,
        description = BOOK_TAG_DESCRIPTION
)
@RestController
@RequestMapping("books")
@PropertySource(value = API_MESSAGES + FILE_FORMAT, encoding = ENCODING, ignoreResourceNotFound = true)
public class BookController {

    private final BookService bookService;
    private final BookCreationRequestMapper bookCreationRequestMapper;
    private final BookResponseMapper bookResponseMapper;

    public BookController(final BookService bookService,
                          final BookCreationRequestMapper bookCreationRequestMapper,
                          final BookResponseMapper bookResponseMapper) {
        this.bookService = bookService;
        this.bookCreationRequestMapper = bookCreationRequestMapper;
        this.bookResponseMapper = bookResponseMapper;
    }

    @Operation(
            summary = BOOK_OPERATION_FIND_BY_ID_SUMMARY,
            description = BOOK_OPERATION_FIND_BY_ID_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = BOOK_RESPONSE_FIND_BY_ID_OK_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookWithAuthorResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = BOOK_RESPONSE_FIND_BY_ID_NOT_FOUND_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = BOOK_RESPONSE_FIND_BY_ID_BAD_REQUEST_DESCRIPTION_ID,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            })
    })
    @GetMapping("id/{id}")
    public ResponseEntity<BookWithAuthorResponse> findById(@Valid
                                                                  @PathVariable
                                                                  @Parameter(required = true)
                                                                  @Id
                                                                  final String id) {
        final var bookWithAuthorResponse = bookResponseMapper.toBookWithAuthorResponse(
                bookService.findById(id)
        );
        return ResponseEntity.ok(bookWithAuthorResponse);
    }

    @Operation(
            summary = BOOK_OPERATION_FIND_BY_ISBN_SUMMARY,
            description = BOOK_OPERATION_FIND_BY_ISBN_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = BOOK_RESPONSE_FIND_BY_ISBN_OK_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookWithAuthorResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = BOOK_RESPONSE_FIND_BY_ISBN_NOT_FOUND_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = BOOK_RESPONSE_FIND_BY_ISBN_BAD_REQUEST_DESCRIPTION_ID,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            })
    })
    @GetMapping("isbn/{isbn}")
    public ResponseEntity<BookWithAuthorResponse> findByIsbn(@Valid
                                                                 @PathVariable
                                                                 @Parameter(required = true)
                                                                 @Isbn13
                                                                 final String isbn) {
        final var bookWithAuthorResponse = bookResponseMapper.toBookWithAuthorResponse(bookService.findByIsbn(isbn));
        return ResponseEntity.ok(bookWithAuthorResponse);
    }

    @Operation(
            summary = BOOK_OPERATION_CREATE_SUMMARY,
            description = BOOK_OPERATION_CREATE_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CREATED,
                    description = BOOK_OPERATION_CREATED_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookWithAuthorResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = BOOK_RESPONSE_CREATE_BAD_REQUEST_DESCRIPTION_ISBN,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = BOOK_RESPONSE_CREATE_BAD_REQUEST_DESCRIPTION_TITLE,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = CONFLICT,
                    description = BOOK_RESPONSE_CREATE_CONFLICT_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            })
    })
    @PostMapping
    public ResponseEntity<BookWithAuthorResponse> register(@RequestBody
                                                               @Valid
                                                               final BookCreationRequest bookCreationRequest) {
        final var book = bookCreationRequestMapper.toBook(bookCreationRequest);
        final var bookWithAuthorResponse = bookResponseMapper.toBookWithAuthorResponse(bookService.register(book));

        return ResponseEntity.status(HttpStatus.CREATED).body(bookWithAuthorResponse);
    }

    @Operation(
            summary = BOOK_OPERATION_FIND_ALL_SUMMARY,
            description = BOOK_OPERATION_FIND_ALL_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = BOOK_RESPONSE_FIND_ALL_OK_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = BookWithAuthorResponse.class))
                    )
            })
    })
    @GetMapping
    public ResponseEntity<List<BookWithAuthorResponse>> findAll() {
        final var bookWithAuthorResponses = bookService.findAll().stream()
                .map(bookResponseMapper::toBookWithAuthorResponse)
                .toList();
        return ResponseEntity.ok(bookWithAuthorResponses);
    }

    @Operation(
            summary = BOOK_OPERATION_DELETE_BY_ID_SUMMARY,
            description = BOOK_OPERATION_DELETE_BY_ID_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = NO_CONTENT,
                    description = BOOK_RESPONSE_DELETE_BY_ID_NO_CONTENT_DESCRIPTION),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = BOOK_RESPONSE_DELETE_BY_ID_NOT_FOUND_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = BOOK_RESPONSE_DELETE_BY_ID_BAD_REQUEST_DESCRIPTION_ID,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            })
    })
    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteById(@Valid
                                               @PathVariable
                                               @Parameter(required = true)
                                               @Id
                                               final String id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = BOOK_OPERATION_DELETE_BY_ISBN_SUMMARY,
            description = BOOK_OPERATION_DELETE_BY_ISBN_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = NO_CONTENT,
                    description = BOOK_RESPONSE_DELETE_BY_ISBN_NO_CONTENT_DESCRIPTION),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = BOOK_RESPONSE_DELETE_BY_ISBN_NOT_FOUND_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = BOOK_RESPONSE_DELETE_BY_ISBN_BAD_REQUEST_DESCRIPTION_ID,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            })
    })
    @DeleteMapping("isbn/{isbn}")
    public ResponseEntity<Void> deleteByIsbn(@Valid
                                                 @PathVariable
                                                 @Parameter(required = true)
                                                 @Isbn13
                                                 final String isbn) {
        bookService.deleteByIsbn(isbn);
        return ResponseEntity.noContent().build();
    }
}