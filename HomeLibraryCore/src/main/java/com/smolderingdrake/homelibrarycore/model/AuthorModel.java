package com.smolderingdrake.homelibrarycore.model;

import com.smolderingdrake.homelibrarycore.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorModel {

    private Long idx;

    @Nullable
    @Length(max = 50, message = "First name should have up to 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be empty")
    @Length(min = 2, max = 50, message = "Last name should have at least 2 characters and up to 50 characters")
    private String lastName;

    public boolean isEqualTo(final Author author) {
        return (nonNull(firstName)
                && this.firstName.equals(author.getFirstName())
                && this.lastName.equals(author.getLastName()))
                ||
                (firstName == null
                && author.getFirstName() == null
                && this.lastName.equals(getLastName())
        );
    }
}
