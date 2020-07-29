package com.smolderingdrake.homelibrarycore.service;

import com.smolderingdrake.homelibrarycore.domain.Book;
import com.smolderingdrake.homelibrarycore.exception.BookException;
import com.smolderingdrake.homelibrarycore.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookByIsbn(final String isbn) {
        return bookRepository.findById(isbn).orElseThrow(() -> new BookException("Book with ISBN " + isbn + "does not exist"));
    }
}
