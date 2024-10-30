package cleverton.heusner.adapter.input.mapper;

import cleverton.heusner.adapter.input.request.AuthorCreationRequest;
import cleverton.heusner.domain.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthorCreationRequestMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(target = "book", ignore = true)
    })
    Author toModel(final AuthorCreationRequest authorCreationRequest);
}