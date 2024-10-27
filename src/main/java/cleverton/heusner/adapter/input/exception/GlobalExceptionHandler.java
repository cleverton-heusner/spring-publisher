package cleverton.heusner.adapter.input.exception;

import cleverton.heusner.domain.exception.BusinessException;
import cleverton.heusner.domain.exception.ExistingResourceException;
import cleverton.heusner.domain.exception.ResourceInUseException;
import cleverton.heusner.domain.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(final ResourceNotFoundException e,
                                                                  final HttpServletRequest request) {
        return createProblemDetail(e, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ExistingResourceException.class)
    public ResponseEntity<Object> handleExistingResourceException(final ExistingResourceException e,
                                                                  final HttpServletRequest request) {
        return createProblemDetail(e, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<Object> handleResourceInUseException(final ResourceInUseException e,
                                                               final HttpServletRequest request) {
        return createProblemDetail(e, HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException e,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatusCode status,
                                                                  final WebRequest request) {
        return handleInvalidRequest(e.getAllErrors(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(final HandlerMethodValidationException e,
                                                                            final HttpHeaders headers,
                                                                            final HttpStatusCode status,
                                                                            final WebRequest request) {
        return handleInvalidRequest(e.getAllErrors(), request);
    }

    private ResponseEntity<Object> handleInvalidRequest(final List<? extends MessageSourceResolvable> errors,
                                                        final WebRequest request) {

        final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors.getFirst()
                .getDefaultMessage());
        problemDetail.setInstance(createUri(request));

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationExceptions(final ConstraintViolationException e,
                                                                      final HttpServletRequest request) {
        return createProblemDetail(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(final BusinessException e,
                                                          final HttpServletRequest request) {
        return createProblemDetail(e, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    private ResponseEntity<Object> createProblemDetail(final RuntimeException e,
                                                       final HttpStatus httpStatus,
                                                       final HttpServletRequest request) {

        final var problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, e.getMessage());
        problemDetail.setInstance(createUri(request));

        return new ResponseEntity<>(problemDetail, httpStatus);
    }

    private <R> URI createUri(final R request) {
        return URI.create(URLEncoder.encode(getServletPath(request), StandardCharsets.UTF_8));
    }

    private <R> String getServletPath(final R request) {
        return request instanceof WebRequest ?
                ((ServletWebRequest) request).getRequest().getServletPath() :
                ((HttpServletRequest) request).getServletPath();
    }
}