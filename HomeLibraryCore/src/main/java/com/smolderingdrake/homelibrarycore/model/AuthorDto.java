package com.smolderingdrake.homelibrarycore.model;

import com.smolderingdrake.homelibrarycore.domain.Author;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorDto {
    AuthorModel authorToAuthorModel(final Author author);
    Author authorModelToAuthor(final AuthorModel authorModel);
}
