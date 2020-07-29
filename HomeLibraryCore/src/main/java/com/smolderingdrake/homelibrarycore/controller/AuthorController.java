package com.smolderingdrake.homelibrarycore.controller;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.domain.Book;
import com.smolderingdrake.homelibrarycore.model.AuthorDto;
import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorDto authorDto;

    public AuthorController(final AuthorService authorService, final AuthorDto authorDto) {
        this.authorService = authorService;
        this.authorDto = authorDto;
    }

    @GetMapping
    public AuthorModels getAll() {
        return new AuthorModels(convertAuthorToAuthorModel(authorService.getAllAuthors()));
    }

    private List<AuthorModel> convertAuthorToAuthorModel(final List<Author> authors) {
        final List<AuthorModel> authorModels = authors.stream().map(authorDto::authorToAuthorModel).collect(Collectors.toList());
        for (int idx = 0; idx < authors.size(); idx++) {
            authorModels.get(idx).setBooks(authors.get(idx).getBooks().stream().map(Book::getTitle).collect(Collectors.toList()));
        }
        return authorModels;
    }

    @GetMapping("/{idx}")
    public AuthorModel getByIdx(@PathVariable final Long idx) {
        return authorDto.authorToAuthorModel(authorService.getByIdx(idx));
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
