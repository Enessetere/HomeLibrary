package com.smolderingdrake.homelibrarycore.service;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.domain.CompositeKey;
import com.smolderingdrake.homelibrarycore.model.AuthorDto;
import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.repository.AuthorRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorDto authorDto;

    public AuthorService(final AuthorRepository authorRepository, final AuthorDto authorDto) {
        this.authorRepository = authorRepository;
        this.authorDto = authorDto;
    }

    public AuthorModels getAllAuthors() {
        return new AuthorModels(authorRepository.findAll().stream()
                .map(authorDto::authorToAuthorModel)
                .collect(Collectors.toList()));
    }

    public AuthorModel getAuthorByName(final String firstName, final String lastName) {
        final CompositeKey index = buildCompositeKey(firstName, lastName);
        final Author existingAuthor = isAuthorExisting(index);
        return authorDto.authorToAuthorModel(existingAuthor);
    }

    private CompositeKey buildCompositeKey(final String firstName, final String lastName) {
        return CompositeKey.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    private Author isAuthorExisting(final CompositeKey index) {
        return authorRepository.findById(index).orElseThrow();      //TODO: exception handler
    }

    public AuthorModel createNewAuthor(final AuthorModel authorModel) {
        final CompositeKey index = buildCompositeKey(authorModel.getFirstName(), authorModel.getLastName());
        isAuthorNotExisting(index);
        final Author author = authorDto.authorModelToAuthor(authorModel);
        authorRepository.save(author);
        return authorModel;
    }

    private void isAuthorNotExisting(final CompositeKey index) {
        authorRepository.findById(index).ifPresent(author -> {
            throw new RuntimeException("Author " + index + " does exist");  //TODO: exception handler
        });
    }

}
