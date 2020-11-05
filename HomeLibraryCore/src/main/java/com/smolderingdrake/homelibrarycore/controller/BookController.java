package com.smolderingdrake.homelibrarycore.controller;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.domain.Book;
import com.smolderingdrake.homelibrarycore.model.*;
import com.smolderingdrake.homelibrarycore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
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
        return new BookModels(convertData(bookService.getAllBooks()));
    }

    private List<BookModel> convertData(final List<Book> books) {
        final List<BookModel> bookModels = books.stream()
                .map(this::convertToBookModel)
                .collect(Collectors.toList());
        bookModels.forEach(this::changeDescriptionToEscapeNewLineCharacter);
        return bookModels;
    }

    private BookModel convertToBookModel(final Book book) {
        final BookModel bookModel = bookDto.bookToBookModel(book);
        bookModel.setAuthors(convertAuthorsToAuthorModels(book.getAuthors()));
        return bookModel;
    }

    private List<AuthorModel> convertAuthorsToAuthorModels(final List<Author> authors) {
        return authors.stream()
                .map(authorDto::authorToAuthorModel)
                .collect(Collectors.toList());
    }

    private void changeDescriptionToEscapeNewLineCharacter(final BookModel book) {
        book.setDescription(book.getDescription().replace("\n", "\\n"));
    }

    @GetMapping("/{isbn}")
    public BookModel getBookById(@PathVariable final String isbn) {
        return convertToBookModel(bookService.getBookByIsbn(isbn));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookModel createNewBook(@RequestBody @Valid final BookModel bookModel) {
        final Book book = bookService.createBook(convertToBook(bookModel));
        return convertToBookModel(book);
    }

    private Book convertToBook(final BookModel bookModel) {
        final Book book = bookDto.bookModelToBook(bookModel);
        book.setAuthors(convertAuthorModelsToAuthors(bookModel.getAuthors()));
        return book;
    }

    private List<Author> convertAuthorModelsToAuthors(final List<AuthorModel> authorModels) {
        return authorModels.stream()
                .map(authorDto::authorModelToAuthor)
                .collect(Collectors.toList());
    }

    @GetMapping("/genre")
    public BookModels getByGenre(@RequestParam("genre") final String genre) {
        return new BookModels(convertData(bookService.getByGenre(genre)));
    }

    @GetMapping("/authors")
    public BookModels getByAuthors(@RequestParam("authors") String idx) {
        return new BookModels(convertData(bookService.getByAuthors(extractIdx(idx))));
    }

    private List<Long> extractIdx(String idx) {
        return Arrays.stream(idx.split("-"))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
