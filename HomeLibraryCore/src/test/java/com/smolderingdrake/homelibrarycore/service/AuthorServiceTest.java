package com.smolderingdrake.homelibrarycore.service;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.exception.AuthorException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    private static final List<Author> EMPTY_LIST = List.of();
    private static final Long FIRST_INDEX = 1L;
    private static final Long SECOND_INDEX = 2L;
    private static final Long THIRD_INDEX = 3L;
    private static final Author FIRST_AUTHOR = Author.builder()
            .idx(FIRST_INDEX)
            .firstName("Adam")
            .lastName("Mickiewicz")
            .build();
    private static final Author SECOND_AUTHOR = Author.builder()
            .idx(SECOND_INDEX)
            .firstName("Simon")
            .lastName("Beckett")
            .build();
    private static final Author THIRD_AUTHOR = Author.builder()
            .idx(THIRD_INDEX)
            .firstName("Anne")
            .lastName("Bishop")
            .build();
    private static final List<Author> AUTHOR_LIST_WITH_TWO_ELEMENTS = List.of(FIRST_AUTHOR, SECOND_AUTHOR);

    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorService authorService;

    @Test
    void shouldReturnAuthorModelsWithEmptyList() {
        when(authorRepository.findAll()).thenReturn(EMPTY_LIST);

        final List<Author> allAuthors = authorService.getAllAuthors();

        assertThat(allAuthors).isNotNull().hasSize(0).isEqualTo(EMPTY_LIST);
    }

    @Test
    void shouldReturnAuthorModelsWithRecords() {
        when(authorRepository.findAll()).thenReturn(AUTHOR_LIST_WITH_TWO_ELEMENTS);

        final List<Author> actualResult = authorService.getAllAuthors();

        assertThat(actualResult).isNotNull().hasSize(2).isEqualTo(AUTHOR_LIST_WITH_TWO_ELEMENTS);
        assertThat(actualResult.get(0)).isEqualTo(FIRST_AUTHOR);
        assertThat(actualResult.get(1)).isEqualTo(SECOND_AUTHOR);
    }

    @Test
    void shouldReturnAuthorModelWithCorrespondingIdx() {
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(FIRST_AUTHOR));

        final Author actualResult = authorService.getByIdx(any(Long.class));

        assertThat(actualResult).isNotNull().isEqualTo(FIRST_AUTHOR);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhileIndexOutOfRange() {
        when(authorRepository.findById(FIRST_INDEX)).thenReturn(Optional.empty());

        final NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getByIdx(FIRST_INDEX));
        assertThat(exception).hasMessage("Author with idx " + FIRST_INDEX + " does not exist");
    }

    @Test
    void shouldCreateNewAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(SECOND_AUTHOR);
        when(authorRepository.findByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(Optional.empty());

        final Author actualResult = authorService.createNewAuthor(SECOND_AUTHOR);

        assertThat(actualResult).isNotNull().isEqualTo(SECOND_AUTHOR);
    }

    @Test
    void shouldThrowExceptionWhenAuthorExist() {
        when(authorRepository.findByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(Optional.of(FIRST_AUTHOR));

        final AuthorException exception = Assertions.assertThrows(AuthorException.class, () -> authorService.createNewAuthor(FIRST_AUTHOR));

        assertThat(exception).hasMessage("Author " + FIRST_AUTHOR.getFirstName() + " " + FIRST_AUTHOR.getLastName() + " already exists");
    }

    @Test
    void shouldDeleteAuthorWithGivenIdx() {
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(FIRST_AUTHOR));

        authorService.deleteAuthor(FIRST_INDEX);
    }

    @Test
    void shouldThrowAuthorExceptionWithRedundantPutMethod() {
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(FIRST_AUTHOR));

        final AuthorException exception = Assertions.assertThrows(AuthorException.class, () -> authorService.editAuthor(FIRST_INDEX, FIRST_AUTHOR));

        assertThat(exception).hasMessage("Author with given details already exists");
    }

    @Test
    void shouldProcessPutMethod() {
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(FIRST_AUTHOR));

        authorService.editAuthor(FIRST_INDEX, THIRD_AUTHOR);
    }

    @Test
    void shouldThrowAuthorExceptionWithRedundantPatchMethod() {
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(FIRST_AUTHOR));

        final AuthorException exception = Assertions.assertThrows(AuthorException.class, () -> authorService.editAuthorFields(FIRST_INDEX, FIRST_AUTHOR));

        assertThat(exception).hasMessage("Author with given details already exists");
    }
    @Test
    void shouldProcessPatchMethod() {
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(FIRST_AUTHOR));

        authorService.editAuthorFields(FIRST_INDEX, SECOND_AUTHOR);
    }
}
