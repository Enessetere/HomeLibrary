package com.smolderingdrake.homelibrarycore.controller;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.domain.Book;
import com.smolderingdrake.homelibrarycore.model.*;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@CrossOrigin
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorDto authorDto;
    private final BookDto bookDto;

    public AuthorController(final AuthorService authorService, final AuthorDto authorDto, final BookDto bookDto) {
        this.authorService = authorService;
        this.authorDto = authorDto;
        this.bookDto = bookDto;
    }

    @GetMapping
    public AuthorModels getAll() {
        return new AuthorModels(convertAuthorsToAuthorModels(authorService.getAllAuthors()));
    }

    private List<AuthorModel> convertAuthorsToAuthorModels(final List<Author> authors) {
        final List<AuthorModel> authorModels = authors.stream().map(authorDto::authorToAuthorModel).collect(Collectors.toList());
        authorModels.forEach(this::changeDescription);
        for (int idx = 0; idx < authors.size(); idx++) {
            authorModels.get(idx).setBooks(convertBooksToBookModels(authors.get(idx).getBooks()));
        }
        return authorModels;
    }

    private void changeDescription(final AuthorModel authorModel) {
        authorModel.setDescription(escapeNewLineInAuthorDescription(authorModel));
    }

    private String escapeNewLineInAuthorDescription(final AuthorModel authorModel) {
        return authorModel.getDescription().replace("\n", "\\n");
    }

    private List<BookModel> convertBooksToBookModels(final List<Book> books) {
        return (!nonNull(books)) ? null : books.stream()
                .map(bookDto::bookToBookModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{idx}")
    public AuthorModel getByIdx(@PathVariable final Long idx) {
        return convertAuthorToAuthorModel(authorService.getByIdx(idx));
    }

    private AuthorModel convertAuthorToAuthorModel(final Author author) {
        final AuthorModel authorModel = authorDto.authorToAuthorModel(author);
        authorModel.setBooks(convertBooksToBookModels(author.getBooks()));
        return authorModel;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthorModel createNewAuthor(@Valid @RequestBody final AuthorModel authorModel) {
        return authorDto.authorToAuthorModel(authorService.createNewAuthor(convertAuthorModelToAuthor(authorModel)));
    }

    private Author convertAuthorModelToAuthor(final AuthorModel model) {
        return authorDto.authorModelToAuthor(model);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idx}")
    public void deleteAuthor(@PathVariable final Long idx) {
        authorService.deleteAuthor(idx);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{idx}")
    public void changeAuthor(@PathVariable final Long idx, @Valid @RequestBody final AuthorModel authorModel) {
        authorService.editAuthor(idx, convertAuthorModelToAuthor(authorModel));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{idx}")
    public void changeAuthorFields(@PathVariable final Long idx, @RequestBody final AuthorModel authorModel) {
        authorService.editAuthorFields(idx, convertAuthorModelToAuthor(authorModel));
    }
}
