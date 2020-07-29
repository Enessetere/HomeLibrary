package com.smolderingdrake.homelibrarycore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookModel {
    @ISBN
    private String isbn;

    @NotNull(message = "Title cannot be empty")
    @Size(min = 2, max = 200, message = "Title should have at least 2 characters and up to 200 characters")
    private String title;

    private List<AuthorModel> authors;

    private Genre genre;

    @Min(value = 0, message = "Count cannot be negative")
    private Integer count;
}
