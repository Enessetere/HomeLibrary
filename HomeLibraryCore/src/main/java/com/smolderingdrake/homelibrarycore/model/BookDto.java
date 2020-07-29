package com.smolderingdrake.homelibrarycore.model;

import com.smolderingdrake.homelibrarycore.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookDto {
    @Mapping(target = "authors", ignore = true)
    Book bookModelToBook(BookModel model);
    @Mapping(target = "authors", ignore = true)
    BookModel bookToBookModel(Book model);
}
