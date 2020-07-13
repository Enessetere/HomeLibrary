package com.smolderingdrake.homelibrarycore.bootstrap;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.repository.AuthorRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("develop")
public class AuthorInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorRepository authorRepository;

    public AuthorInitializer(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        authorRepository.save(Author.builder().firstName("Adam").lastName("Mickiewicz").build());
        authorRepository.save(Author.builder().firstName("Henryk").lastName("Sienkiewicz").build());
        authorRepository.save(Author.builder().firstName("Simon").lastName("Beckett").build());
    }
}
