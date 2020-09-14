package com.smolderingdrake.homelibrarycore.model;

import com.smolderingdrake.homelibrarycore.domain.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AuthorDto {
    @Mapping(target = "books", ignore = true)
    AuthorModel authorToAuthorModel(final Author author);
    @Mapping(target = "books", ignore = true)
    Author authorModelToAuthor(final AuthorModel authorModel);
}
