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

    @GetMapping("/{fullName}")
    public AuthorModel getByName(@PathVariable final String fullName) {
        return authorService.getAuthorByName(fullName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthorModel createNewAuthor(@Valid @RequestBody final AuthorModel authorModel) {
        return authorService.createNewAuthor(authorModel);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{fullName}")
    public void deleteAuthor(@PathVariable final String fullName) {
        authorService.deleteAuthor(fullName);
    }

    //TODO: PUT Request
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{fullName}")
    public void changeAuthor(@PathVariable final String fullName, @Valid @RequestBody final AuthorModel authorModel) {
        authorService.editAuthor(fullName, authorModel);
    }

    //TODO: PATCH Request
    //TODO: Unit Tests for AuthorController
}
