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
            @Mapping(target = "book", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true)
    })
    AuthorEntity toEntity(final Author author);

    @Mapping(target = "book", ignore = true)
    Author toModel(final AuthorEntity authorEntity);
}