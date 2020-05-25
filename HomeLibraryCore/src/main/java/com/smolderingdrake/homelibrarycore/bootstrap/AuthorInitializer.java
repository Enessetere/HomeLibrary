package com.smolderingdrake.homelibrarycore.bootstrap;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.repository.AuthorRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthorInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorRepository authorRepository;

    public AuthorInitializer(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        authorRepository.save(new Author("Adam", "Mickiewicz"));
        authorRepository.save(new Author("Henryk", "Sienkiewicz"));
        authorRepository.save(new Author("Simon", "Beckett"));
    }
}
