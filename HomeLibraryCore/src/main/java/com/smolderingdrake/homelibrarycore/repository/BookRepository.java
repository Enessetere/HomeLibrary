package com.smolderingdrake.homelibrarycore.repository;

import com.smolderingdrake.homelibrarycore.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
