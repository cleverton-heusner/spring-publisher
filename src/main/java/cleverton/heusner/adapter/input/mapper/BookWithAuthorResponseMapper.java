package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.adapter.input.response.BookWithAuthorResponse;
import cleverton.heusner.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookWithAuthorResponseMapper {

    @Mapping(source = "author", target = "authorWithoutBookResponse")
    BookWithAuthorResponse toResponse(final Book book);
}