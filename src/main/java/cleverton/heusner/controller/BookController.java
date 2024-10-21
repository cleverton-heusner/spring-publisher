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
import static cleverton.heusner.controller.HttpStatusCode.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(
        name= "book.tag.name",
        description = "book.tag.description"
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

    @Operation(summary = "book.operation.find-by-id.summary",
            description = "book.operation.find-by-id.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = "book.response.find-by-id.ok.description",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookWithAuthorResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = "book.response.find-by-id.not-found.description",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "book.response.find-by-id.bad-request.description.id",
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
            summary = "book.operation.find-by-isbn.summary",
            description = "book.operation.find-by-isbn.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = "book.response.find-by-isbn.ok.description",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookWithAuthorResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = "book.response.find-by-isbn.not-found.description",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "book.response.find-by-isbn.bad-request.description.id",
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
            summary = "book.operation.create.summary",
            description = "book.operation.create.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CREATED,
                    description = "book.operation.created.description",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookWithAuthorResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "book.response.create.bad-request.description.isbn",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "book.response.create.bad-request.description.title",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = CONFLICT,
                    description = "book.response.create.conflict.description",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            })
    })
    @PostMapping
    public ResponseEntity<BookWithAuthorResponse> register(@RequestBody @Valid final BookCreationRequest bookCreationRequest) {
        final var book = bookCreationRequestMapper.toBook(bookCreationRequest);
        final var bookWithAuthorResponse = bookResponseMapper.toBookWithAuthorResponse(bookService.register(book));

        return ResponseEntity.status(HttpStatus.CREATED).body(bookWithAuthorResponse);
    }

    @Operation(
            summary = "book.operation.find-all.summary",
            description = "book.operation.find-all.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = "book.response.find-all.ok.description",
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
            summary = "book.operation.delete-by-id.summary",
            description = "book.operation.delete-by-id.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = NO_CONTENT,
                    description = "book.response.delete-by-id.no-content.description"),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = "book.response.delete-by-id.not-found.description",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "book.response.delete-by-id.bad-request.description.id",
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
            summary = "book.operation.delete-by-isbn.summary",
            description = "book.operation.delete-by-isbn.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = NO_CONTENT,
                    description = "book.response.delete-by-isbn.no-content.description"),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = "book.response.delete-by-isbn.not-found.description",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "book.response.delete-by-isbn.bad-request.description.id",
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