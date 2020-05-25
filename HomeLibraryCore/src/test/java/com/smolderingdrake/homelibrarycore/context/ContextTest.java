package com.smolderingdrake.homelibrarycore.context;

import com.smolderingdrake.homelibrarycore.controller.AuthorController;
import com.smolderingdrake.homelibrarycore.repository.AuthorRepository;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ContextTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorController authorController;

    @Test
    void isAuthorRepositoryCreated() {
        assertThat(authorRepository).isNotNull();
    }

    @Test
    void isAuthorServiceCreated() {
        assertThat(authorService).isNotNull();
    }

    @Test
    void isAuthorControllerCreated() {
        assertThat(authorController).isNotNull();
    }
}
