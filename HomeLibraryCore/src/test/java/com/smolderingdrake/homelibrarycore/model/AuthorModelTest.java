package com.smolderingdrake.homelibrarycore.model;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    // region: Happy path

    @Test
    void shouldValidateAuthorModelWithLastNameOnly() {
        final AuthorModel author = AuthorModel.builder().lastName("Doe").build();

        validate(author);
    }

    @Test
    void shouldValidateAuthorModelWithFirstAndLastName() {
        final AuthorModel author = AuthorModel.builder().firstName("Jane").lastName("Doe").build();

        validate(author);
    }

    @Test
    void shouldValidateAuthorModelWithFirstAndLastNameAndOtherFields() {
        final AuthorModel authorWithNoIdx = AuthorModel.builder().firstName("Jane").lastName("Doe").idx(null).build();
        final AuthorModel authorWithIdx = AuthorModel.builder().firstName("Jane").lastName("Doe").idx(5L).build();

        validate(authorWithIdx);
        validate(authorWithNoIdx);
    }

    // endregion

    // region: Border path

    @Test
    void shouldValidateAuthorModelWithBorderValues() {
        final AuthorModel authorMidValues = AuthorModel.builder().firstName("Ag").lastName("Bl").build();
        final AuthorModel authorMinValues = AuthorModel.builder().lastName("Bl").build();
        final AuthorModel authorMaxValues = AuthorModel.builder()
                .firstName("Asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;")
                .lastName("Asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;")
                .build();

        validate(authorMinValues);
        validate(authorMidValues);
        validate(authorMaxValues);
    }

    // endregion

    // region: Error path
    @Test
    void shouldHaveErrorWhenLastNameIsShorterThanTwo() {
        final AuthorModel author = AuthorModel.builder().lastName("A").build();

        validate(author,"Last name should have at least 2 characters and up to 50 characters", "lastName");
    }

    @Test
    void shouldHaveErrorWhenLastNameIsLongerThanFifty() {
        final AuthorModel author = AuthorModel.builder().lastName("Asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;a").build();

        validate(author, "Last name should have at least 2 characters and up to 50 characters", "lastName");
    }

    @Test
    void shouldHaveErrorWhenFirstNameIsShorterThanTwo() {
        final AuthorModel author = AuthorModel.builder().firstName("A").lastName("Ab").build();

        validate(author, "First name should have up to 50 characters", "firstName");
    }

    @Test
    void shouldHaveErrorWhenFirstNameIsLongerThanFifty() {
        final AuthorModel author = AuthorModel.builder().firstName("Asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;asdfghjkl;a").lastName("Ab").build();

        validate(author, "First name should have up to 50 characters", "firstName");
    }

    @Test
    void shouldHaveErrorWhenFirstOrLastNameStartsWithLowerCaseLetter() {
        final String expectedMessage = "First and last name should start with capital letter";
        final String expectedField = "nameValid";
        final AuthorModel authorWithWrongFirstName = AuthorModel.builder().firstName("jane").lastName("Doe").build();
        final AuthorModel authorWithWrongLastName = AuthorModel.builder().firstName("Jane").lastName("doe").build();
        final AuthorModel authorWithWrongName = AuthorModel.builder().firstName("jane").lastName("doe").build();

        validate(authorWithWrongFirstName, expectedMessage, expectedField);
        validate(authorWithWrongLastName, expectedMessage, expectedField);
        validate(authorWithWrongName, expectedMessage, expectedField);
    }

    // endregion

    // region: Support method

    private void validate(final AuthorModel author) {
        assertNoValidationError(validator.validate(author));
    }

    private void assertNoValidationError(final Set<ConstraintViolation<AuthorModel>> errors) {
        assertThat(errors).hasSize(0);
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

    // endregion
}
