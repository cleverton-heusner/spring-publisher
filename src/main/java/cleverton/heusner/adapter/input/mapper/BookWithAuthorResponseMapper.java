package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.adapter.input.response.BookWithAuthorResponse;
import cleverton.heusner.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookWithAuthorResponseMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "isbn", target = "isbn"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "author.id", target = "authorWithoutBookResponse.id"),
            @Mapping(source = "author.name", target = "authorWithoutBookResponse.name")
    })
    BookWithAuthorResponse toResponse(final Book book);
}