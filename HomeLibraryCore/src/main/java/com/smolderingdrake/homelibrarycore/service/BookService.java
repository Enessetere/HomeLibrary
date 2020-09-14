package com.smolderingdrake.homelibrarycore.service;

import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.domain.Book;
import com.smolderingdrake.homelibrarycore.exception.BookException;
import com.smolderingdrake.homelibrarycore.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(final BookRepository bookRepository, final AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookByIsbn(final String isbn) {
        return bookRepository.findById(isbn).orElseThrow(() -> new BookException("Book with ISBN " + isbn + " does not exist"));
    }

    public Book createBook(final Book book) {
        if (nonNull(isBookExisting(book))) {
            throw new BookException("Book with ISBN " + book.getIsbn() + " already exist");
        }
        createMissingAuthors(book.getAuthors());
        return bookRepository.save(book);
    }

    private Book isBookExisting(final Book book) {
        return bookRepository.findById(book.getIsbn()).orElse(null);
    }

    private void createMissingAuthors(final List<Author> authors) {
        authors.stream()
                .filter(this::isAuthorNotExisting)
                .forEach(authorService::createNewAuthor);
    }

    private boolean isAuthorNotExisting(final Author author) {
        return !authorService.isAuthorExisting(author);
    }


}
