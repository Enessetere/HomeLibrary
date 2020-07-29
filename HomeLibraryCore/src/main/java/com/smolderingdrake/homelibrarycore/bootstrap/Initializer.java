package com.smolderingdrake.homelibrarycore.bootstrap;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.domain.Book;
import com.smolderingdrake.homelibrarycore.model.Genre;
import com.smolderingdrake.homelibrarycore.repository.BookRepository;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("develop")
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Author SIMON_BECKETT = Author.builder().firstName("Simon").lastName("Beckett").build();
    private static final Author ANNE_BISHOP = Author.builder().firstName("Anne").lastName("Bishop").build();
    private static final Author JK_ROWLING = Author.builder().firstName("J. K.").lastName("Rowling").build();
    private static final Book BOOK_EXAMPLE = Book.builder().isbn("9788324148455").count(1).genre(Genre.CRIMINAL).authors(List.of(SIMON_BECKETT)).title("The chemistry of death").build();

    private final AuthorService authorService;
    private final BookRepository bookRepository;

    public Initializer(final AuthorService authorService, final BookRepository bookRepository) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        authorService.createNewAuthor(SIMON_BECKETT);
        authorService.createNewAuthor(ANNE_BISHOP);
        authorService.createNewAuthor(JK_ROWLING);
        bookRepository.save(BOOK_EXAMPLE);
    }
}
