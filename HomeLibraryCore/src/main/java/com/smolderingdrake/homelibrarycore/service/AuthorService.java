package com.smolderingdrake.homelibrarycore.service;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.model.AuthorDto;
import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.repository.AuthorRepository;
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

    public AuthorModel getAuthorByName(final String fullName) {
        final Author existingAuthor = getExistingAuthorDispatcher(fullName);
        return authorDto.authorToAuthorModel(existingAuthor);
    }

    private Author getExistingAuthorDispatcher(final String fullName) {
        final String[] name = fullName.split("-");
        if (name.length == 2) {
            return getExistingAuthor(name[1], name[0]);
        }
        if (name.length == 1) {
            return getExistingAuthor(name[0]);
        }
        throw new RuntimeException();                                                                   //TODO: exception handler
    }

    private Author getExistingAuthor(final String firstName, final String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow();          //TODO: exception handler
    }

    private Author getExistingAuthor(final String lastName) {
        return authorRepository.findByLastNameAndFirstNameIsNull(lastName).orElseThrow();                //TODO: exception handler
    }

    public AuthorModel createNewAuthor(final AuthorModel authorModel) {
        isAuthorExist(authorModel);
        final Author author = authorDto.authorModelToAuthor(authorModel);
        authorRepository.save(author);
        return authorModel;
    }
    private void isAuthorExist(final AuthorModel authorModel) {
        authorRepository.findByFirstNameAndLastName(authorModel.getLastName(), authorModel.getFirstName()).ifPresent(author -> {
            throw new RuntimeException();                                                               //TODO: exception handler
        });
    }

    public void deleteAuthor(final String fullName) {
        final Author existingAuthor = getExistingAuthorDispatcher(fullName);
        authorRepository.delete(existingAuthor);
    }

    public void editAuthor(final String fullName, final AuthorModel authorModel) {
        final Author existingAuthor = getExistingAuthorDispatcher(fullName);
        if (!existingAuthor.getLastName().equals(authorModel.getLastName())) {
            isAuthorExist(authorModel);
            authorRepository.delete(existingAuthor);
            authorRepository.save(authorDto.authorModelToAuthor(authorModel));
        } else {
            existingAuthor.setFirstName(authorModel.getFirstName());
            authorRepository.save(existingAuthor);
        }
    }

    public void editAuthorFields(final String fullName, final AuthorModel authorModel) {
        final Author author = getExistingAuthorDispatcher(fullName);
        if (author.getLastName().equals(authorModel.getLastName())) {
            author.setFirstName(authorModel.getFirstName());
        }
    }

    //TODO: Unit tests for AuthorService
}
