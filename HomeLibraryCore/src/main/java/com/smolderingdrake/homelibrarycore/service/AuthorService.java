package com.smolderingdrake.homelibrarycore.service;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.model.AuthorDto;
import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

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

    public AuthorModel getByIdx(final Long idx) {
        final Author existingAuthor = getExistingAuthor(idx);
        return authorDto.authorToAuthorModel(existingAuthor);
    }

    private Author getExistingAuthor(final Long idx) {
        return authorRepository.findById(idx).orElseThrow();
    }

    public AuthorModel createNewAuthor(final AuthorModel authorModel) {
        if (isAuthorExisting(authorModel)) {
            throw new RuntimeException();
        }
        authorModel.setIdx(createAndReturnIdx(authorModel));
        return authorModel;
    }

    private boolean isAuthorExisting(final AuthorModel authorModel) {
        return authorRepository.findByFirstNameAndLastName(authorModel.getFirstName(), authorModel.getLastName()).isPresent();
    }

    private Long createAndReturnIdx(final AuthorModel authorModel) {
        return authorRepository.save(authorDto.authorModelToAuthor(authorModel)).getIdx();
    }

    public void deleteAuthor(final Long idx) {
        final Author existingAuthor = getExistingAuthor(idx);
        authorRepository.delete(existingAuthor);
    }

    public void editAuthor(final Long idx, final AuthorModel authorModel) {
        final Author existingAuthor = getExistingAuthor(idx);
        if (!authorModel.isEqualTo(existingAuthor) && !isAuthorExisting(authorModel)) {
            existingAuthor.setFirstName(authorModel.getFirstName());
            existingAuthor.setLastName(authorModel.getLastName());
            authorRepository.save(existingAuthor);
        } else {
            throw new RuntimeException();
        }
    }

    public void editAuthorFields(final Long idx, final AuthorModel authorModel) {
        final Author existingAuthor = getExistingAuthor(idx);
        if (!authorModel.isEqualTo(existingAuthor) && !isAuthorExisting(authorModel)) {
            if (nonNull(authorModel.getFirstName())) {
                existingAuthor.setFirstName(authorModel.getFirstName());
            }
            if (nonNull(authorModel.getLastName())) {
                existingAuthor.setLastName(authorModel.getLastName());
            }
            authorRepository.save(existingAuthor);
        } else {
            throw new RuntimeException();
        }
    }


    //TODO: Unit tests for AuthorService
}
