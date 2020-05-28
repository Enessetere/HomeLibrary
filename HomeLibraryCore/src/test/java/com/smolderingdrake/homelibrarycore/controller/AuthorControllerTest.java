package com.smolderingdrake.homelibrarycore.controller;

import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    private AuthorController noProxyAuthorController;

    @Mock
    private AuthorService authorService;

    @BeforeEach
    void setup() {
        noProxyAuthorController = new AuthorController(authorService);
    }

    @Test
    void shouldReturnAuthorModelList() {
        final AuthorModels authors = new AuthorModels(List.of(AuthorModel.builder()
                .idx(1L)
                .firstName("Adam")
                .lastName("Poziomka")
                .build()));
        when(authorService.getAllAuthors()).thenReturn(authors);

        final AuthorModels models = noProxyAuthorController.getAll();

        assertThat(models).isNotNull();
        assertThat(models).isExactlyInstanceOf(AuthorModels.class);
        assertThat(models).isEqualTo(authors);
        assertThat(models.getAuthors().size()).isEqualTo(1);
    }

}
