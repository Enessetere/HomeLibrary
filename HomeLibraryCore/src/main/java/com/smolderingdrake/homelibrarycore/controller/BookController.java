package com.smolderingdrake.homelibrarycore.controller;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.domain.Book;
import com.smolderingdrake.homelibrarycore.model.*;
import com.smolderingdrake.homelibrarycore.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookDto bookDto;
    private final AuthorDto authorDto;

    public BookController(final BookService bookService, final BookDto bookDto, final AuthorDto authorDto) {
        this.bookService = bookService;
        this.bookDto = bookDto;
        this.authorDto = authorDto;
    }

    @GetMapping
    public BookModels getAllBooks() {
        return new BookModels(fetchData(bookService.getAllBooks()));
    }

    private List<BookModel> fetchData(final List<Book> books) {
        return bookService.getAllBooks().stream()
                .map(this::convertToBookModel)
                .collect(Collectors.toList());
    }

    private BookModel convertToBookModel(final Book book) {
        final BookModel bookModel = bookDto.bookToBookModel(book);
        bookModel.setAuthors(convertAuthorsToAuthorsModels(book.getAuthors()));
        return bookModel;
    }

    private List<AuthorModel> convertAuthorsToAuthorsModels(final List<Author> authors) {
        return authors.stream()
                .map(authorDto::authorToAuthorModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{isbn}")
    public BookModel getBookById(@PathVariable final String isbn) {
        return convertToBookModel(bookService.getBookByIsbn(isbn));
    }
}
