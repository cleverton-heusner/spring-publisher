package cleverton.heusner.mapper.author;

import cleverton.heusner.dto.author.AuthorCreationRequest;
import cleverton.heusner.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthorCreationRequestMapper {

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "birthDate", target = "birthDate"),

            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(target = "book", ignore = true)
    })
    Author toAuthor(final AuthorCreationRequest authorCreationRequest);
}