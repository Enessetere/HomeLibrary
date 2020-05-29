package com.smolderingdrake.homelibrarycore.model;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorModelTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldHaveErrorWhenLastNameIsShorterThanTwo() {
        final AuthorModel author = AuthorModel.builder().lastName("a").build();

        validate(author,"Last name should have at least 2 characters and up to 50 characters", "lastName");
    }

    private void validate(final AuthorModel author, final String expectedMessage, final String expectedField) {
        assertSingleErrorOnFieldWithMessage(validator.validate(author), expectedMessage, expectedField);
    }

    private void assertSingleErrorOnFieldWithMessage(final Set<ConstraintViolation<AuthorModel>> errors,
                                                     final String expectedMessage, final String expectedField) {
        assertThat(errors).hasSize(1);
        assertThat(errors.stream()).allMatch(x -> x.getMessage().equals(expectedMessage)
                && ((PathImpl)x.getPropertyPath()).getLeafNode().getName().equals(expectedField));
    }

    @Test
    void shouldHaveErrorWhenLastNameIsEmpty() {
        final AuthorModel author = AuthorModel.builder().build();

        validate(author, "Last name cannot be empty", "lastName");
    }

    @Test
    void shouldHaveErrorWhenLastNameIsLongerThanFifty() {
        final AuthorModel author = AuthorModel.builder().lastName("asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;a").build();

        validate(author, "Last name should have at least 2 characters and up to 50 characters", "lastName");
    }
}
