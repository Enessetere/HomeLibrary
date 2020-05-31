package com.smolderingdrake.homelibrarycore.service;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.model.AuthorDto;
import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(allAuthors).isExactlyInstanceOf(AuthorModels.class);
        assertThat(allAuthors.getAuthors()).isNotNull();
        assertThat(allAuthors.getAuthors()).hasSize(0);
    }

    @Test
    void shouldReturnAuthorModelsWithRecords() {
        final Long idx = 1L;
        final String firstName = "Jane";
        final String lastName = "Doe";
        final Author author = Author.builder().idx(idx).firstName(firstName).lastName(lastName).build();
        final AuthorModel authorModel = AuthorModel.builder().idx(idx).firstName(firstName).lastName(lastName).build();

        final List<Author> authors = List.of(author);
        when(authorRepository.findAll()).thenReturn(authors);
        when(authorDto.authorToAuthorModel(any(Author.class))).thenReturn(authorModel);

        final AuthorModels allAuthors = authorService.getAllAuthors();

        assertThat(allAuthors).isNotNull();
        assertThat(allAuthors).isExactlyInstanceOf(AuthorModels.class);
        assertThat(allAuthors.getAuthors()).isNotNull();
        assertThat(allAuthors.getAuthors()).hasSize(1);
        assertThat(allAuthors.getAuthors().get(0)).isExactlyInstanceOf(AuthorModel.class);
        assertThat(allAuthors.getAuthors().get(0)).isEqualTo(authorModel);
    }
}
