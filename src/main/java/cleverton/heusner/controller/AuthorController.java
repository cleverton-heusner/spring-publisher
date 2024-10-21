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
import static cleverton.heusner.controller.HttpStatusCode.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@Tag(
        name = "author.tag.name",
        description = "author.tag.description"
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
            summary = "author.operation.find-by-id.summary",
            description = "author.operation.find-by-id.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = "author.response.find-by-id.ok.description",
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorWithBookResponse.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = "author.response.find-by-id.not-found.description",
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "author.response.find-by-id.bad-request.description.id",
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
            summary = "author.operation.find-by-name.summary",
            description = "author.operation.find-by-name.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = "author.response.find-by-name.ok.description",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorWithBookResponse.class)
                    )
            }),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = "author.response.find-by-name.not-found.description",
                    content = { @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "author.response.find-by-name.bad-request.description.name",
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
                                                                 @Size(min = 1, max = 20, message = "{Size.author.name}")
                                                                 final String name) {
        final var authorWithBookResponse = authorResponseMapper.toAuthorWithBookResponse(authorService.findByName(name));
        return ResponseEntity.ok(authorWithBookResponse);
    }

    @Operation(
            summary = "author.operation.create.summary",
            description = "author.operation.create.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CREATED,
                    description = "author.operation.created.description",
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorWithBookResponse.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "author.response.create.bad-request.description.name",
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = CONFLICT,
                    description = "author.response.create.conflict.description",
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
            summary = "author.operation.find-all.summary",
            description = "author.operation.find-all.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = "author.response.find-all.ok.description",
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
            summary = "author.operation.delete-by-id.summary",
            description = "author.operation.delete-by-id.description"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = NO_CONTENT,
                    description = "author.response.delete-by-id.no-content.description"
            ),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = "author.response.delete-by-id.not-found.description",
                    content = {@Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    }),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "author.response.delete-by-id.bad-request.description.id",
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