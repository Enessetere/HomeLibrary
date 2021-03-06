package com.smolderingdrake.homelibrarycore.bootstrap;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.domain.Book;
import com.smolderingdrake.homelibrarycore.model.Genre;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import com.smolderingdrake.homelibrarycore.service.BookService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("develop")
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Author SIMON_BECKETT = Author.builder().firstName("Simon").lastName("Beckett").description("Criminal writer").build();
    private static final Author ANNE_BISHOP = Author.builder().firstName("Anne").lastName("Bishop").description("Fantasy/romance writer.").build();
    private static final Author JK_ROWLING = Author.builder().firstName("J. K.").lastName("Rowling").description("Fantasy writer.").build();
    private static final Author GREG_MCLEAN_HALL = Author.builder().firstName("Greg").lastName("McLean Hall").description("science writer").build();
    private static final Author CRAIG_WALLS = Author.builder().firstName("Craig").lastName("Walls").description("science writer").build();
    private static final Book CHEMISTRY_OF_DEATH = Book.builder().isbn("9788324148455").description("1st part of David Hunter history.").count(1).genre(Genre.CRIMINAL).authors(List.of(SIMON_BECKETT)).title("The Chemistry of Death").build();
    private static final Book WRITTEN_IN_BONE = Book.builder().isbn("9788324151639").description("2nd part of David Hunter history.").count(1).genre(Genre.CRIMINAL).authors(List.of(SIMON_BECKETT)).title("Written in Bone").build();
    private static final Book ADAPTIVE_CODE = Book.builder().isbn("9788328338708").description("Adaptive Code.Nothing more").count(1).genre(Genre.SCIENCE).authors(List.of(GREG_MCLEAN_HALL)).title("Adaptive Code").build();
    private static final Book SPRING_IN_ACTION_4TH = Book.builder().isbn("9788328308497").description("Spring in Action. 4th edition.").count(1).genre(Genre.SCIENCE).authors(List.of(CRAIG_WALLS)).title("Spring in Action. 4th edition").build();
    private static final Book SPRING_IN_ACTION_5TH = Book.builder().isbn("9788328356061").description("Spring in Action. 5th edition.").count(1).genre(Genre.SCIENCE).authors(List.of(CRAIG_WALLS)).title("Spring in Action. 5th edition").build();

    private final AuthorService authorService;
    private final BookService bookService;

    public Initializer(final AuthorService authorService, final BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        authorService.createNewAuthor(ANNE_BISHOP);
        authorService.createNewAuthor(JK_ROWLING);
        bookService.createBook(CHEMISTRY_OF_DEATH);
        bookService.createBook(WRITTEN_IN_BONE);
        bookService.createBook(ADAPTIVE_CODE);
        bookService.createBook(SPRING_IN_ACTION_4TH);
        bookService.createBook(SPRING_IN_ACTION_5TH);
    }
}
