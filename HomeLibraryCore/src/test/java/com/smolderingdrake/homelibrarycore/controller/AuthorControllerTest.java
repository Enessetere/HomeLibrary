package com.smolderingdrake.homelibrarycore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smolderingdrake.homelibrarycore.model.AuthorModel;
import com.smolderingdrake.homelibrarycore.model.AuthorModels;
import com.smolderingdrake.homelibrarycore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

    private AuthorController noProxyAuthorController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private AuthorService authorService;

    @BeforeEach
    void init() {
        noProxyAuthorController = new AuthorController(authorService);
        final StandaloneMockMvcBuilder mvcBuilder = MockMvcBuilders.standaloneSetup(noProxyAuthorController);
        this.mockMvc = mvcBuilder.build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturnAuthorModelsWithOneRecord() throws Exception {
        final AuthorModel author = AuthorModel.builder()
                .idx(1L)
                .firstName("Adam")
                .lastName("Poziomka")
                .build();
        final AuthorModels authors = new AuthorModels(List.of(author));
        when(authorService.getAllAuthors()).thenReturn(authors);


        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final AuthorModels models = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {});
        assertThat(models).isNotNull();
        assertThat(models).isExactlyInstanceOf(AuthorModels.class);
        assertThat(models.getAuthors().get(0)).isExactlyInstanceOf(AuthorModel.class);
        assertThat(models.getAuthors().get(0)).isEqualTo(author);
        assertThat(models).isEqualTo(authors);
        assertThat(models.getAuthors().size()).isEqualTo(authors.getAuthors().size());
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
                new TypeReference<>() {});
        assertThat(models).isNotNull();
        assertThat(models).isExactlyInstanceOf(AuthorModels.class);
        assertThat(models.getAuthors()).isNull();
        assertThat(models).isEqualTo(authors);
    }

    @Test
    void shouldReturnAuthorByIdxWithFirstAndLastName() throws Exception {
        final Long idx = 1L;
        final String firstName = "Adam";
        final String lastName = "Mickiewicz";
        final AuthorModel author = AuthorModel.builder().idx(idx).firstName(firstName).lastName(lastName).build();
        when(authorService.getByIdx(idx)).thenReturn(author);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/" + idx)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final AuthorModel model = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {});
        assertThat(model).isNotNull();
        assertThat(model).isExactlyInstanceOf(AuthorModel.class);
        assertThat(model).isEqualTo(author);
    }

    @Test
    void shouldReturnAuthorByIdxWithLastName() throws Exception {
        final Long idx = 1L;
        final String lastName = "Homer";
        final AuthorModel author = AuthorModel.builder().idx(idx).lastName(lastName).build();
        when(authorService.getByIdx(idx)).thenReturn(author);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/" + idx)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final AuthorModel model = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {});
        assertThat(model).isNotNull();
        assertThat(model).isExactlyInstanceOf(AuthorModel.class);
        assertThat(model).isEqualTo(author);
    }

    @Test
    void shouldCreateNewAuthorWithFirstAndLastName() throws Exception {
        final Long idx = 1L;
        final String firstName = "Adam";
        final String lastName = "Mickiewicz";
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
                new TypeReference<>() {});

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isExactlyInstanceOf(AuthorModel.class);
        assertThat(actualResult).isEqualTo(result);
    }

    @Test
    void shouldCreateNewAuthorWithLastName() throws Exception {
        final Long idx = 1L;
        final String lastName = "Homer";
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
                new TypeReference<>() {});
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isExactlyInstanceOf(AuthorModel.class);
        assertThat(actualResult).isEqualTo(result);
    }
}
