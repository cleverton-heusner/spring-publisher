package cleverton.heusner.controller;

import cleverton.heusner.dto.author.AuthorCreationRequest;
import cleverton.heusner.dto.author.AuthorWithBookResponse;
import cleverton.heusner.mapper.author.AuthorCreationRequestMapper;
import cleverton.heusner.mapper.author.AuthorResponseMapper;
import cleverton.heusner.service.author.AuthorService;
import cleverton.heusner.validation.id.Id;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cleverton.heusner.configuration.message.MessageBasename.API_MESSAGES;
import static cleverton.heusner.configuration.message.MessageConfiguration.ENCODING;
import static cleverton.heusner.configuration.message.MessageConfiguration.FILE_FORMAT;
import static cleverton.heusner.constant.documentation.api.AuthorApiDoc.*;
import static cleverton.heusner.constant.api.HttpStatusCode.*;
import static cleverton.heusner.constant.message.validation.AuthorMessageValidation.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@Tag(
        name = AUTHOR_TAG_NAME,
        description = AUTHOR_TAG_DESCRIPTION
)
@RestController
@RequestMapping("authors")
@PropertySource(value = API_MESSAGES + FILE_FORMAT, encoding = ENCODING, ignoreResourceNotFound = true)
public class AuthorController {

    private final AuthorCreationRequestMapper authorCreationRequestMapper;
    private final AuthorResponseMapper authorResponseMapper;
    private final AuthorService authorService;

    public AuthorController(final AuthorCreationRequestMapper authorCreationRequestMapper,
                            final AuthorResponseMapper authorResponseMapper,
                            final AuthorService authorService) {
        this.authorCreationRequestMapper = authorCreationRequestMapper;
        this.authorResponseMapper = authorResponseMapper;
        this.authorService = authorService;
    }

    @Operation(
            summary = AUTHOR_OPERATION_FIND_BY_ID_SUMMARY,
            description = AUTHOR_OPERATION_FIND_BY_ID_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = AUTHOR_RESPONSE_FIND_BY_ID_OK_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorWithBookResponse.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = AUTHOR_RESPONSE_FIND_BY_ID_NOT_FOUND_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = AUTHOR_RESPONSE_FIND_BY_ID_BAD_REQUEST_DESCRIPTION_ID,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    })
    })
    @GetMapping("id/{id}")
    public ResponseEntity<AuthorWithBookResponse> findById(@Valid
                                                               @PathVariable
                                                               @Parameter(required = true)
                                                               @Id
                                                               final String id) {
        final var authorWithBookResponse = authorResponseMapper.toAuthorWithBookResponse(
                authorService.findById(id)
        );
        return ResponseEntity.ok(authorWithBookResponse);
    }
    @Operation(
            summary = AUTHOR_OPERATION_FIND_BY_NAME_SUMMARY,
            description = AUTHOR_OPERATION_FIND_BY_NAME_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = AUTHOR_RESPONSE_FIND_BY_NAME_OK_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorWithBookResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = AUTHOR_RESPONSE_FIND_BY_NAME_NOT_FOUND_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = AUTHOR_RESPONSE_FIND_BY_NAME_BAD_REQUEST_DESCRIPTION_NAME,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    })
    })
    @GetMapping("name/{name}")
    public ResponseEntity<AuthorWithBookResponse> findByName(@Valid
                                                                 @PathVariable
                                                                 @Parameter(required = true)
                                                                 @Size(
                                                                         min = AUTHOR_NAME_MIN_SIZE,
                                                                         max = AUTHOR_NAME_MAX_SIZE,
                                                                         message = SIZE_AUTHOR_NAME
                                                                 )
                                                                 final String name) {
        final var authorWithBookResponse = authorResponseMapper.toAuthorWithBookResponse(authorService.findByName(name));
        return ResponseEntity.ok(authorWithBookResponse);
    }

    @Operation(
            summary = AUTHOR_OPERATION_CREATE_SUMMARY,
            description = AUTHOR_OPERATION_CREATE_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CREATED,
                    description = AUTHOR_OPERATION_CREATED_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorWithBookResponse.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = AUTHOR_RESPONSE_CREATE_BAD_REQUEST_DESCRIPTION_NAME,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = CONFLICT,
                    description = AUTHOR_RESPONSE_CREATE_CONFLICT_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    })
    })
    @PostMapping
    public ResponseEntity<AuthorWithBookResponse> register(@RequestBody @Valid final AuthorCreationRequest authorCreationRequest) {
        final var author = authorCreationRequestMapper.toAuthor(authorCreationRequest);
        final var savedAuthor = authorService.register(author);

        return ResponseEntity.status(HttpStatus.CREATED).body(authorResponseMapper.toAuthorWithBookResponse(savedAuthor));
    }

    @Operation(
            summary = AUTHOR_OPERATION_FIND_ALL_SUMMARY,
            description = AUTHOR_OPERATION_FIND_ALL_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = AUTHOR_RESPONSE_FIND_ALL_OK_DESCRIPTION,
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = AuthorWithBookResponse.class))
                    )
                    })
    })
    @GetMapping
    public ResponseEntity<List<AuthorWithBookResponse>> findAll() {
        final List<AuthorWithBookResponse> authorWithBookResponses = authorService.findAll().stream()
                .map(authorResponseMapper::toAuthorWithBookResponse)
                .toList();
        return ResponseEntity.ok(authorWithBookResponses);
    }

    @Operation(
            summary = AUTHOR_OPERATION_DELETE_BY_ID_SUMMARY,
            description = AUTHOR_OPERATION_DELETE_BY_ID_DESCRIPTION
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = NO_CONTENT,
                    description = AUTHOR_RESPONSE_DELETE_BY_ID_NO_CONTENT_DESCRIPTION
            ),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = AUTHOR_RESPONSE_DELETE_BY_ID_NOT_FOUND_DESCRIPTION,
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = AUTHOR_RESPONSE_DELETE_BY_ID_BAD_REQUEST_DESCRIPTION_ID,
                    content = {@Content(
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
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}