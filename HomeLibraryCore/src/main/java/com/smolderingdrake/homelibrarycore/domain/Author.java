package com.smolderingdrake.homelibrarycore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Length(max = 50, message = "First name should have up to 50 characters")
    @Column(name = "first_name")
    private String firstName;

    @Id
    @NotNull(message = "Last name cannot be empty")
    @Length(min = 2, max = 50, message = "Last name should have at least 2 characters and up to 50 characters")
    @Column(name = "last_name")
    private String lastName;
}
