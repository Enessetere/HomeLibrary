package com.smolderingdrake.homelibrarycore.controller;

import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class AuthorControllerTest {

    private AuthorController noProxyAuthorController;

    @Mock
    private AuthorService authorService;

    @BeforeEach
    void init() {
        noProxyAuthorController = new AuthorController(authorService);
    }

    @Test
    void shouldReturnAuthorModelsWithOneRecord() {
        final AuthorModels authors = new AuthorModels(List.of(AuthorModel.builder()
                .idx(1L)
                .firstName("Adam")
                .lastName("Poziomka")
                .build()));
        when(authorService.getAllAuthors()).thenReturn(authors);

        final AuthorModels models = noProxyAuthorController.getAll();

        assertThat(models).isNotNull();
        assertThat(models).isExactlyInstanceOf(AuthorModels.class);
        assertThat(models.getAuthors().get(0)).isExactlyInstanceOf(AuthorModel.class);
        assertThat(models).isEqualTo(authors);
        assertThat(models.getAuthors().size()).isEqualTo(1);
    }

    @Test
    void shouldReturnAuthorModelsWithEmptyList() {
        final AuthorModels authors = new AuthorModels();
        when(authorService.getAllAuthors()).thenReturn(authors);

        final AuthorModels models = noProxyAuthorController.getAll();

        assertThat(models).isNotNull();
        assertThat(models).isExactlyInstanceOf(AuthorModels.class);
        assertThat(models.getAuthors()).isNull();
        assertThat(models).isEqualTo(authors);
    }

    @Test
    void shouldReturnAuthorByIdxWithFirstAndLastName() {
        final Long idx = 1L;
        final String firstName = "Adam";
        final String lastName = "Mickiewicz";
        final AuthorModel author = AuthorModel.builder().idx(idx).firstName(firstName).lastName(lastName).build();
        when(authorService.getByIdx(idx)).thenReturn(author);

        final AuthorModel model = noProxyAuthorController.getByIdx(idx);

        assertThat(model).isNotNull();
        assertThat(model).isExactlyInstanceOf(AuthorModel.class);
        assertThat(model).isEqualTo(author);
    }

    @Test
    void shouldReturnAuthorByIdxWithLastName() {
        final Long idx = 1L;
        final String lastName = "Homer";
        final AuthorModel author = AuthorModel.builder().idx(idx).lastName(lastName).build();
        when(authorService.getByIdx(idx)).thenReturn(author);

        final AuthorModel model = noProxyAuthorController.getByIdx(idx);

        assertThat(model).isNotNull();
        assertThat(model).isExactlyInstanceOf(AuthorModel.class);
        assertThat(model).isEqualTo(author);
    }

    @Test
    void shouldCreateNewAuthorWithFirstAndLastName() {
        final Long idx = 1L;
        final String firstName = "Adam";
        final String lastName = "Mickiewicz";
        final AuthorModel result = AuthorModel.builder().idx(idx).firstName(firstName).lastName(lastName).build();
        final AuthorModel input = AuthorModel.builder().firstName(firstName).lastName(lastName).build();
        when(authorService.createNewAuthor(input)).thenReturn(result);

        final AuthorModel actualResult = noProxyAuthorController.createNewAuthor(input);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isExactlyInstanceOf(AuthorModel.class);
        assertThat(actualResult).isEqualTo(result);
    }

    @Test
    void shouldCreateNewAuthorWithLastName() {
        final Long idx = 1L;
        final String lastName = "Homer";
        final AuthorModel input = AuthorModel.builder().lastName(lastName).build();
        final AuthorModel result = AuthorModel.builder().idx(idx).lastName(lastName).build();
        when(authorService.createNewAuthor(input)).thenReturn(result);

        final AuthorModel actualResult = noProxyAuthorController.createNewAuthor(input);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isExactlyInstanceOf(AuthorModel.class);
        assertThat(actualResult).isEqualTo(result);
    }
}
