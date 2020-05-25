package com.smolderingdrake.homelibrarycore.repository;

import com.smolderingdrake.homelibrarycore.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, String> {

    Optional<Author> findByFirstNameAndLastName(final String firstName, final String lastName);
    Optional<Author> findByLastNameAndFirstNameIsNull(final String lastName);
}
