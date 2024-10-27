package cleverton.heusner.adapter.output.mapper;

import cleverton.heusner.adapter.output.entity.AuthorEntity;
import cleverton.heusner.domain.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthorEntityMapper {

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "birthDate", target = "birthDate"),

            @Mapping(target = "book", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true)
    })
    AuthorEntity toEntity(final Author author);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "birthDate", target = "birthDate"),
            @Mapping(source = "createdDate", target = "createdDate"),
            @Mapping(source = "lastModifiedDate", target = "lastModifiedDate"),

            @Mapping(target = "book", ignore = true)
    })
    Author toModel(final AuthorEntity authorEntity);
}