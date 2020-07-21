package com.smolderingdrake.homelibrarycore.bootstrap;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.repository.AuthorRepository;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("develop")
public class AuthorInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Author SIMON_BECKETT = Author.builder().firstName("Simon").lastName("Beckett").build();
    private static final Author ANNE_BISHOP = Author.builder().firstName("Anne").lastName("Bishop").build();
    private static final Author JK_ROWLING = Author.builder().firstName("J. K.").lastName("Rowling").build();

    private final AuthorService authorService;

    public AuthorInitializer(final AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        authorService.createNewAuthor(SIMON_BECKETT);
        authorService.createNewAuthor(ANNE_BISHOP);
        authorService.createNewAuthor(JK_ROWLING);
    }
}
