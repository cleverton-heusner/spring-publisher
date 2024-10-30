package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.adapter.input.response.AuthorWithBookResponse;
import cleverton.heusner.domain.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthorWithBookResponseMapper {

    @Mapping(source = "book", target = "bookWithoutAuthorResponse")
    AuthorWithBookResponse toResponse(final Author author);
}