package cleverton.heusner.mapper.book;

import cleverton.heusner.model.Book;
import cleverton.heusner.dto.book.BookWithAuthorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookResponseMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "isbn", target = "isbn"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "author.id", target = "authorWithoutBookResponse.id"),
            @Mapping(source = "author.name", target = "authorWithoutBookResponse.name")
    })
    BookWithAuthorResponse toBookWithAuthorResponse(final Book book);
}