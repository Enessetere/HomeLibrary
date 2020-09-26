package com.smolderingdrake.homelibrarycore.service;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.exception.AuthorException;
import com.smolderingdrake.homelibrarycore.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.nonNull;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getByIdx(final Long idx) {
        return getExistingAuthor(idx);
    }

    private Author getExistingAuthor(final Long idx) {
        return authorRepository.findById(idx)
                .orElseThrow(() -> new NoSuchElementException("Author with idx " + idx + " does not exist"));
    }

    public Author createNewAuthor(final Author author) {
        if (isAuthorExisting(author)) {
            throw new AuthorException("Author " + author.getFirstName() + " " + author.getLastName() + " already exists");
        }
        author.setIdx(null);
        return authorRepository.save(author);
    }

    public boolean isAuthorExisting(final Author author) {
        return authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName()).isPresent();
    }

    public void deleteAuthor(final Long idx) {
        final Author existingAuthor = getExistingAuthor(idx);
        authorRepository.delete(existingAuthor);
    }

    public void editAuthor(final Long idx, final Author author) {
        isAuthorExisting(author);
        final Author existingAuthor = getExistingAuthor(idx);
        if (!author.equals(existingAuthor)) {
            existingAuthor.setFirstName(author.getFirstName());
            existingAuthor.setLastName(author.getLastName());
            authorRepository.save(existingAuthor);
        } else {
            throw new AuthorException("Author with given details already exists");
        }
    }

    public void editAuthorFields(final Long idx, final Author author) {
        isAuthorExisting(author);
        final Author existingAuthor = getExistingAuthor(idx);
        if (!author.equals(existingAuthor)) {
            if (nonNull(author.getFirstName())) {
                existingAuthor.setFirstName(author.getFirstName());
            }
            if (nonNull(author.getLastName())) {
                existingAuthor.setLastName(author.getLastName());
            }
            isAuthorExisting(existingAuthor);
            authorRepository.save(existingAuthor);
        } else {
            throw new AuthorException("Author with given details already exists");
        }
    }
}
