package com.smolderingdrake.homelibrarycore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smolderingdrake.homelibrarycore.domain.Author;
import com.smolderingdrake.homelibrarycore.model.AuthorDto;
import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.model.BookDto;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    private static final String JANE = "Jane";
    private static final String DOE = "Doe";
    private static final String SMITH = "Smith";
    private static final String DESCRIPTION = "Criminal writer";

    private static final Author AUTHOR_TEST_DATA_1 = Author.builder().firstName(JANE).lastName(DOE).description(DESCRIPTION).build();
    private static final Author AUTHOR_TEST_DATA_2 = Author.builder().lastName(SMITH).description(DESCRIPTION).build();
    private static final AuthorModel AUTHOR_MODEL_TEST_DATA_1 = AuthorModel.builder().firstName(JANE).lastName(DOE).description(DESCRIPTION).build();
    private static final AuthorModel AUTHOR_MODEL_TEST_DATA_2 = AuthorModel.builder().lastName(SMITH).description(DESCRIPTION).build();
    private static final List<Author> AUTHOR_LIST_WITH_SINGLE_ELEMENT = List.of(AUTHOR_TEST_DATA_1);
    private static final List<Author> AUTHOR_LIST_WITH_TWO_ELEMENTS = List.of(AUTHOR_TEST_DATA_1, AUTHOR_TEST_DATA_2);
    private static final List<Author> AUTHOR_LIST_WITH_NO_ELEMENTS = List.of();

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private AuthorService authorService;

    @Mock
    private AuthorDto authorDto;

    @Mock
    private BookDto bookDto;

    @BeforeEach
    void init() {
        AuthorController noProxyAuthorController = new AuthorController(authorService, authorDto, bookDto);
        final StandaloneMockMvcBuilder mvcBuilder = MockMvcBuilders.standaloneSetup(noProxyAuthorController);
        this.mockMvc = mvcBuilder.build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturnAuthorModelsWithOneRecord() throws Exception {
        // given
        when(authorService.getAllAuthors()).thenReturn(AUTHOR_LIST_WITH_SINGLE_ELEMENT);
        when(authorDto.authorToAuthorModel(any())).thenReturn(AUTHOR_MODEL_TEST_DATA_1);

        // when
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        final AuthorModels models = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(models).isNotNull().isExactlyInstanceOf(AuthorModels.class);
        assertThat(models.getAuthors().get(0)).isExactlyInstanceOf(AuthorModel.class).isEqualTo(AUTHOR_MODEL_TEST_DATA_1);
        assertThat(models.getAuthors()).hasSize(1);
    }

    @Test
    void shouldReturnAuthorModelsWithMultipleRecords() throws Exception {
        // given
        when(authorService.getAllAuthors()).thenReturn(AUTHOR_LIST_WITH_TWO_ELEMENTS);
        when(authorDto.authorToAuthorModel(AUTHOR_TEST_DATA_1)).thenReturn(AUTHOR_MODEL_TEST_DATA_1);
        when(authorDto.authorToAuthorModel(AUTHOR_TEST_DATA_2)).thenReturn(AUTHOR_MODEL_TEST_DATA_2);

        //when
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //than
        final AuthorModels result = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(result).isNotNull().isExactlyInstanceOf(AuthorModels.class);
        assertThat(result.getAuthors()).isNotNull().hasSize(2);
        assertThat(result.getAuthors().get(0)).isExactlyInstanceOf(AuthorModel.class).isEqualTo(AUTHOR_MODEL_TEST_DATA_1);
        assertThat(result.getAuthors().get(1)).isExactlyInstanceOf(AuthorModel.class).isEqualTo(AUTHOR_MODEL_TEST_DATA_2);
    }

    @Test
    void shouldReturnAuthorModelsWithEmptyList() throws Exception {
        // given
        when(authorService.getAllAuthors()).thenReturn(AUTHOR_LIST_WITH_NO_ELEMENTS);

        // when
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // than
        final AuthorModels models = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(models).isNotNull().isExactlyInstanceOf(AuthorModels.class);
        assertThat(models.getAuthors()).hasSize(0);
    }

    @Test
    void shouldReturnAuthorByIdxWithFirstAndLastName() throws Exception {
        // given
        when(authorService.getByIdx(any(Long.class))).thenReturn(AUTHOR_TEST_DATA_1);
        when(authorDto.authorToAuthorModel(any(Author.class))).thenReturn(AUTHOR_MODEL_TEST_DATA_1);

        // when
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/" + any(Long.class))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        final AuthorModel model = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(model).isNotNull().isExactlyInstanceOf(AuthorModel.class).isEqualTo(AUTHOR_MODEL_TEST_DATA_1);
    }

    @Test
    void shouldReturnAuthorByIdxWithLastName() throws Exception {
        // given
        when(authorService.getByIdx(any(Long.class))).thenReturn(AUTHOR_TEST_DATA_2);
        when(authorDto.authorToAuthorModel(AUTHOR_TEST_DATA_2)).thenReturn(AUTHOR_MODEL_TEST_DATA_2);

        // when
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/" + any(Long.class))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        final AuthorModel model = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(model).isNotNull().isExactlyInstanceOf(AuthorModel.class).isEqualTo(AUTHOR_MODEL_TEST_DATA_2);
    }

    @Test
    void shouldCreateNewAuthorWithFirstAndLastName() throws Exception {
        // given
        when(authorService.createNewAuthor(AUTHOR_TEST_DATA_1)).thenReturn(AUTHOR_TEST_DATA_1);
        when(authorDto.authorModelToAuthor(AUTHOR_MODEL_TEST_DATA_1)).thenReturn(AUTHOR_TEST_DATA_1);
        when(authorDto.authorToAuthorModel(AUTHOR_TEST_DATA_1)).thenReturn(AUTHOR_MODEL_TEST_DATA_1);

        // when
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                .content(objectMapper.writeValueAsString(AUTHOR_MODEL_TEST_DATA_1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        final AuthorModel actualResult = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(actualResult)
                .isNotNull()
                .isExactlyInstanceOf(AuthorModel.class)
                .isEqualTo(AUTHOR_MODEL_TEST_DATA_1);
    }

    @Test
    void shouldCreateNewAuthorWithLastName() throws Exception {
        // given
        when(authorService.createNewAuthor(AUTHOR_TEST_DATA_2)).thenReturn(AUTHOR_TEST_DATA_2);
        when(authorDto.authorToAuthorModel(AUTHOR_TEST_DATA_2)).thenReturn(AUTHOR_MODEL_TEST_DATA_2);
        when(authorDto.authorModelToAuthor(AUTHOR_MODEL_TEST_DATA_2)).thenReturn(AUTHOR_TEST_DATA_2);

        // when
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                .content(objectMapper.writeValueAsString(AUTHOR_MODEL_TEST_DATA_2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // then
        final AuthorModel actualResult = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(actualResult)
                .isNotNull()
                .isExactlyInstanceOf(AuthorModel.class)
                .isEqualTo(AUTHOR_MODEL_TEST_DATA_2);
    }

    @Test
    void shouldReturnBadRequestCodeDueToValidationInPostMethod() throws Exception {
        final AuthorModel authorInitials = AuthorModel.builder().firstName("J").lastName("d").build();
        final AuthorModel authorLastNameInitial = AuthorModel.builder().lastName("D").build();
        final AuthorModel authorFirstNameInitialAndLastName = AuthorModel.builder().firstName("J").lastName("Doe").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                    .content(objectMapper.writeValueAsString(authorInitials))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                    .content(objectMapper.writeValueAsString(authorLastNameInitial))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                    .content(objectMapper.writeValueAsString(authorFirstNameInitialAndLastName))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldReturnNoContentStatusDueCorrectDataInPutMethod() throws Exception {
        final AuthorModel author = AuthorModel.builder().idx(1L).firstName("Jane").lastName("Doe").build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/authors/1")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void shouldReturnBadRequestStatusDueValidationDataInPutMethod() throws Exception {
        final AuthorModel author = AuthorModel.builder().idx(1L).firstName("J").lastName("D").build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/authors/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldReturnNoContentStatusInPatchMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/authors/" + any(Long.class))
                    .content(objectMapper.writeValueAsString(AUTHOR_MODEL_TEST_DATA_1))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void shouldReturnNoContentStatusInDeleteMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/authors/" + any(Long.class))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
