package com.smolderingdrake.homelibrarycore.service;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.exception.AuthorException;
import com.smolderingdrake.homelibrarycore.model.AuthorDto;
import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {


    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorDto authorDto;
    @InjectMocks
    private AuthorService authorService;

    @Test
    void shouldReturnAuthorModelsWithEmptyList() {
        when(authorRepository.findAll()).thenReturn(List.of());

        final AuthorModels allAuthors = authorService.getAllAuthors();

        assertThat(allAuthors).isNotNull();
        assertThat(allAuthors.getAuthors()).isNotNull();
        assertThat(allAuthors.getAuthors()).hasSize(0);
    }

    @Test
    void shouldReturnAuthorModelsWithRecords() {
        final Long first_idx = 1L;
        final String first_firstName = "Jane";
        final String first_lastName = "Doe";
        final Author first_author = Author.builder().idx(first_idx).firstName(first_firstName).lastName(first_lastName).build();
        final AuthorModel first_authorModel = AuthorModel.builder().idx(first_idx).firstName(first_firstName).lastName(first_lastName).build();
        final Long second_idx = 2L;
        final String second_firstName = "John";
        final String second_lastName = "DoeDee";
        final Author second_author = Author.builder().idx(second_idx).firstName(second_firstName).lastName(second_lastName).build();
        final AuthorModel second_authorModel = AuthorModel.builder().idx(second_idx).firstName(second_firstName).lastName(second_lastName).build();

        final List<Author> authors = List.of(first_author, second_author);
        when(authorRepository.findAll()).thenReturn(authors);
        when(authorDto.authorToAuthorModel(any(Author.class))).thenReturn(first_authorModel).thenReturn(second_authorModel);

        final AuthorModels allAuthors = authorService.getAllAuthors();

        assertThat(allAuthors).isNotNull();
        assertThat(allAuthors.getAuthors()).isNotNull();
        assertThat(allAuthors.getAuthors()).hasSize(2);
        assertThat(allAuthors.getAuthors().get(0)).isEqualTo(first_authorModel);
        assertThat(allAuthors.getAuthors().get(1)).isEqualTo(second_authorModel);
    }

    @Test
    void shouldReturnAuthorModelWithCorrespondingIdx() {
        final Long idx = 1L;
        final String firstName = "Jane";
        final String lastName = "Doe";
        final Author author = Author.builder().idx(idx).firstName(firstName).lastName(lastName).build();
        final AuthorModel authorModel = AuthorModel.builder().idx(idx).firstName(firstName).lastName(lastName).build();
        when(authorRepository.findById(idx)).thenReturn(Optional.of(author));
        when(authorDto.authorToAuthorModel(author)).thenReturn(authorModel);

        final AuthorModel result = authorService.getByIdx(idx);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(authorModel);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhileIndexOutOfRange() {
        final Long idx = 1L;
        when(authorRepository.findById(idx)).thenReturn(Optional.empty());

        final NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getByIdx(idx));
        assertThat(exception).hasMessage("Author with idx " + idx + " does not exist");
    }

    @Test
    void shouldCreateNewAuthor() {
        final Long idx = 5L;
        final String firstName = "Jane";
        final String lastName = "Doe";
        final AuthorModel input = AuthorModel.builder().idx(idx).firstName(firstName).lastName(lastName).build();
        final Author author = Author.builder().idx(1L).firstName(firstName).lastName(lastName).build();
        when(authorRepository.save(author)).thenReturn(author);
        when(authorDto.authorModelToAuthor(input)).thenReturn(author);
        when(authorRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Optional.empty());

        final AuthorModel result = authorService.createNewAuthor(input);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(input);
        assertThat(result.getIdx()).isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionWhenAuthorExist() {
        final Long idx = 1L;
        final String firstName = "Jane";
        final String lastName = "Doe";
        final AuthorModel input = AuthorModel.builder().firstName(firstName).lastName(lastName).build();
        final Author author = Author.builder().idx(idx).firstName(firstName).lastName(lastName).build();
        when(authorRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(Optional.of(author));

        final AuthorException exception = Assertions.assertThrows(AuthorException.class, () -> authorService.createNewAuthor(input));

        assertThat(exception).hasMessage("Author with given details already exists");
    }
}
