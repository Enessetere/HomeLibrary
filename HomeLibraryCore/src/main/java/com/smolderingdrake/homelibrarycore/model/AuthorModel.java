package com.smolderingdrake.homelibrarycore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smolderingdrake.homelibrarycore.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import java.util.List;

import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorModel {

    private Long idx;

    @Length(min = 2, max = 50, message = "First name should have up to 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be empty")
    @Length(min = 2, max = 50, message = "Last name should have at least 2 characters and up to 50 characters")
    private String lastName;

    private List<String> books;

    public boolean isEqualTo(final Author author) {
        return (!nonNull(firstName)
                || !this.firstName.equals(author.getFirstName())
                || !this.lastName.equals(author.getLastName()))
                && (firstName != null
                        || author.getFirstName() != null
                        || !this.lastName.equals(getLastName()));
    }

    @JsonIgnore
    @AssertTrue(message = "First and last name should start with capital letter")
    private boolean isNameValid() {
        return lastName != null
                && (firstName == null || Character.isUpperCase(firstName.charAt(0)))
                && Character.isUpperCase(lastName.charAt(0));
    }
}
