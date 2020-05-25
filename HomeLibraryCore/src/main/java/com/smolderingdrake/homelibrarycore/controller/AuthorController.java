package com.smolderingdrake.homelibrarycore.controller;

import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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

    @GetMapping("/{firstName}-{lastName}")
    public AuthorModel getByName(@PathVariable final String firstName, @PathVariable final String lastName) {
        return authorService.getAuthorByName(firstName, lastName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthorModel createNewAuthor(@Valid @RequestBody final AuthorModel authorModel) {
        return authorService.createNewAuthor(authorModel);
    }
}
