package com.smolderingdrake.homelibrarycore.controller;

import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(final AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public AuthorModels getAll() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{idx}")
    public AuthorModel getByName(@PathVariable final Long idx) {
        return authorService.getByIdx(idx);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthorModel createNewAuthor(@Valid @RequestBody final AuthorModel authorModel) {
        return authorService.createNewAuthor(authorModel);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{idx}")
    public void deleteAuthor(@PathVariable final Long idx) {
        authorService.deleteAuthor(idx);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{idx}")
    public void changeAuthor(@PathVariable final Long idx, @Valid @RequestBody final AuthorModel authorModel) {
        authorService.editAuthor(idx, authorModel);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{idx}")
    public void changeAuthorFields(@PathVariable final Long idx, @RequestBody final AuthorModel authorModel) {
        authorService.editAuthorFields(idx, authorModel);
    }


    //TODO: Unit Tests for AuthorController
}
