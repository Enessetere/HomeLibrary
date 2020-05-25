package com.smolderingdrake.homelibrarycore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorModel {

    @Nullable
    @Length(max = 50, message = "First name should have up to 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be empty")
    @Length(min = 2, max = 50, message = "Last name should have at least 2 characters and up to 50 characters")
    private String lastName;
}
