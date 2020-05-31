package com.smolderingdrake.homelibrarycore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private AuthorService authorService;

    @BeforeEach
    void init() {
        AuthorController noProxyAuthorController = new AuthorController(authorService);
        final StandaloneMockMvcBuilder mvcBuilder = MockMvcBuilders.standaloneSetup(noProxyAuthorController);
        this.mockMvc = mvcBuilder.build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturnAuthorModelsWithOneRecord() throws Exception {
        final AuthorModel author = AuthorModel.builder()
                .idx(1L)
                .firstName("Jane")
                .lastName("Doe")
                .build();
        final AuthorModels authors = new AuthorModels(List.of(author));
        when(authorService.getAllAuthors()).thenReturn(authors);


        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final AuthorModels models = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(models).isNotNull();
        assertThat(models).isExactlyInstanceOf(AuthorModels.class);
        assertThat(models.getAuthors().get(0)).isExactlyInstanceOf(AuthorModel.class);
        assertThat(models.getAuthors().get(0)).isEqualTo(author);
        assertThat(models).isEqualTo(authors);
        assertThat(models.getAuthors()).hasSize(1);
    }

    @Test
    void shouldReturnAuthorModelsWithMultipleRecords() throws Exception {
        final AuthorModel authorWithBothNames = AuthorModel.builder().idx(1L).firstName("Jane").lastName("Doe").build();
        final AuthorModel authorWithLastName = AuthorModel.builder().idx(2L).lastName("Doe").build();
        final AuthorModels authors = new AuthorModels(List.of(authorWithBothNames, authorWithLastName));
        when(authorService.getAllAuthors()).thenReturn(authors);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final AuthorModels result = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(AuthorModels.class);
        assertThat(result.getAuthors()).isNotNull();
        assertThat(result.getAuthors()).hasSize(2);
        assertThat(result.getAuthors().get(0)).isExactlyInstanceOf(AuthorModel.class);
        assertThat(result.getAuthors().get(0)).isEqualTo(authorWithBothNames);
        assertThat(result.getAuthors().get(1)).isExactlyInstanceOf(AuthorModel.class);
        assertThat(result.getAuthors().get(1)).isEqualTo(authorWithLastName);
    }

    @Test
    void shouldReturnAuthorModelsWithEmptyList() throws Exception {
        final AuthorModels authors = new AuthorModels();
        when(authorService.getAllAuthors()).thenReturn(authors);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final AuthorModels models = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(models).isNotNull();
        assertThat(models).isExactlyInstanceOf(AuthorModels.class);
        assertThat(models.getAuthors()).isNull();
        assertThat(models).isEqualTo(authors);
    }

    @Test
    void shouldReturnAuthorByIdxWithFirstAndLastName() throws Exception {
        final Long idx = 1L;
        final String firstName = "Jane";
        final String lastName = "Doe";
        final AuthorModel author = AuthorModel.builder().idx(idx).firstName(firstName).lastName(lastName).build();
        when(authorService.getByIdx(idx)).thenReturn(author);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/" + idx)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final AuthorModel model = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(model).isNotNull();
        assertThat(model).isExactlyInstanceOf(AuthorModel.class);
        assertThat(model).isEqualTo(author);
    }

    @Test
    void shouldReturnAuthorByIdxWithLastName() throws Exception {
        final Long idx = 1L;
        final String lastName = "Doe";
        final AuthorModel author = AuthorModel.builder().idx(idx).lastName(lastName).build();
        when(authorService.getByIdx(idx)).thenReturn(author);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/" + idx)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final AuthorModel model = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(model).isNotNull();
        assertThat(model).isExactlyInstanceOf(AuthorModel.class);
        assertThat(model).isEqualTo(author);
    }

    @Test
    void shouldCreateNewAuthorWithFirstAndLastName() throws Exception {
        final Long idx = 1L;
        final String firstName = "Jane";
        final String lastName = "Doe";
        final AuthorModel result = AuthorModel.builder().idx(idx).firstName(firstName).lastName(lastName).build();
        final AuthorModel input = AuthorModel.builder().firstName(firstName).lastName(lastName).build();
        when(authorService.createNewAuthor(input)).thenReturn(result);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                .content(objectMapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        final AuthorModel actualResult = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isExactlyInstanceOf(AuthorModel.class);
        assertThat(actualResult).isEqualTo(result);
    }

    @Test
    void shouldCreateNewAuthorWithLastName() throws Exception {
        final Long idx = 1L;
        final String lastName = "Doe";
        final AuthorModel input = AuthorModel.builder().lastName(lastName).build();
        final AuthorModel result = AuthorModel.builder().idx(idx).lastName(lastName).build();
        when(authorService.createNewAuthor(input)).thenReturn(result);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                .content(objectMapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        final AuthorModel actualResult = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isExactlyInstanceOf(AuthorModel.class);
        assertThat(actualResult).isEqualTo(result);
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
        final AuthorModel author = AuthorModel.builder().firstName("Jane").build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/authors/1")
                    .content(objectMapper.writeValueAsString(author))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void shouldReturnNoContentStatusInDeleteMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/authors/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
