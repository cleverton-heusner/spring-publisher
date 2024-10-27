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
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "isbn", target = "isbn"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "createdDate", target = "createdDate"),
            @Mapping(source = "lastModifiedDate", target = "lastModifiedDate"),

            @Mapping(source = "authorEntity.id", target = "author.id"),
            @Mapping(source = "authorEntity.name", target = "author.name"),
            @Mapping(source = "authorEntity.birthDate", target = "author.birthDate"),
            @Mapping(source = "authorEntity.createdDate", target = "author.createdDate"),
            @Mapping(source = "authorEntity.lastModifiedDate", target = "author.lastModifiedDate"),

            @Mapping(target = "author.book", ignore = true)
    })
    Book toModel(final BookEntity bookEntity);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "isbn", target = "isbn"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "createdDate", target = "createdDate"),
            @Mapping(source = "lastModifiedDate", target = "lastModifiedDate"),

            @Mapping(source = "author", target = "authorEntity")
    })
    BookEntity toEntity(final Book book);
}