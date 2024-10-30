package cleverton.heusner.adapter.output.mapper;

import cleverton.heusner.adapter.output.entity.BookEntity;
import cleverton.heusner.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookEntityMapper {

    @Mappings({
            @Mapping(source = "authorEntity", target = "author"),
            @Mapping(target = "author.book", ignore = true)
    })
    Book toModel(final BookEntity bookEntity);

    @Mapping(source = "author", target = "authorEntity")
    BookEntity toEntity(final Book book);
}