package cleverton.heusner.mapper.book;

import cleverton.heusner.model.Book;
import cleverton.heusner.dto.book.BookCreationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookCreationRequestMapper {

    @Mappings({
            @Mapping(source = "isbn", target = "isbn"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "authorName", target = "author.name"),

            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true)
    })
    Book toBook(final BookCreationRequest bookCreationRequest);
}