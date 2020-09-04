package com.smolderingdrake.homelibrarycore.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity(name = "authors")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "books")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    @Length(max = 50, message = "First name should have up to 50 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last name cannot be empty")
    @Length(min = 2, max = 50, message = "Last name should have at least 2 characters and up to 50 characters")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public boolean equals(Author author) {
        if (this == author) return true;
        if (author == null) return false;
        return firstName.equals(author.firstName)
                && lastName.equals(author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
