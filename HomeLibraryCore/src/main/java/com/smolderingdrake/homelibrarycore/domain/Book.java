package com.smolderingdrake.homelibrarycore.domain;

import com.smolderingdrake.homelibrarycore.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "books")
public class Book {

    @Id
    @ISBN
    private String isbn;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(name = "books_to_authors",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name = "no_of_copies")
    private Integer count;
}
