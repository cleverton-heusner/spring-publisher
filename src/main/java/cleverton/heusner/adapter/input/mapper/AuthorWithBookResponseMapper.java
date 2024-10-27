package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.adapter.input.response.AuthorWithBookResponse;
import cleverton.heusner.domain.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthorWithBookResponseMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "birthDate", target = "birthDate"),
            @Mapping(source = "createdDate", target = "createdDate"),
            @Mapping(source = "lastModifiedDate", target = "lastModifiedDate"),
            @Mapping(source = "book.id", target = "bookWithoutAuthorResponse.id"),
            @Mapping(source = "book.isbn", target = "bookWithoutAuthorResponse.isbn"),
            @Mapping(source = "book.title", target = "bookWithoutAuthorResponse.title"),
            @Mapping(source = "book.createdDate", target = "bookWithoutAuthorResponse.createdDate"),
            @Mapping(source = "book.lastModifiedDate", target = "bookWithoutAuthorResponse.lastModifiedDate")
    })
    AuthorWithBookResponse toResponse(final Author author);
}