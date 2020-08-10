package com.inoastrum.pharmaquestionservice.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inoastrum.pharmaquestionservice.services.QuestionService;
import com.inoastrum.pharmaquestionservice.web.model.QuestionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@ExtendWith(MockitoExtension.class)
@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuestionService questionService;

    @Autowired
    ObjectMapper objectMapper;

    QuestionDto getValidDto() {
        return QuestionDto.builder()
                .pharmacyId(UUID.randomUUID())
                .questionText("This is a question text bla bla bla?")
                .questionerAccountId(UUID.randomUUID())
                .questionImageLinks(List.of(URI.create("https://www.google.com")))
                .build();
    }

    @Test
    void getQuestionById() throws Exception {
        given(questionService.findQuestionDtoById(any(UUID.class))).willReturn(getValidDto());

        ConstrainedFields fields = new ConstrainedFields(QuestionDto.class);

        mockMvc.perform(get("/api/v1/question/{questionId}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/question-get",
                        pathParameters(
                                parameterWithName("questionId").description("UUID of desired question to get")
                        ),
                        responseFields(
                                fields.withPath("id").description("ID of the question"),
                                fields.withPath("version").description("API Version of the question"),
                                fields.withPath("createdDate").description("Creation Date"),
                                fields.withPath("lastModifiedDate").description("Last Modification Date"),
                                fields.withPath("questionerAccountId").description("Account ID of the questioner"),
                                fields.withPath("pharmacyId").description("Pharmacy ID of which is going to answer the question"),
                                fields.withPath("questionText").description("The question text"),
                                fields.withPath("questionImageLinks").description("List of URIs to the question images if exists").optional()
                        )));
    }

    @Test
    void createNewQuestion() throws Exception {
        String questionDtoJson = objectMapper.writeValueAsString(getValidDto());

        ConstrainedFields fields = new ConstrainedFields(QuestionDto.class);

        given(questionService.saveNewQuestion(any(QuestionDto.class))).willReturn(getValidDto());

        mockMvc.perform(post("/api/v1/question/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(questionDtoJson)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andDo(document("v1/question-post",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("questionerAccountId").description("Account ID of the questioner"),
                                fields.withPath("pharmacyId").description("Pharmacy ID of which is going to answer the question"),
                                fields.withPath("questionText").description("The question text"),
                                fields.withPath("questionImageLinks").description("List of URIs to the question images if exists").optional()
                        )));
    }

    @Test
    void updateQuestion() throws Exception {
        String questionDtoJson = objectMapper.writeValueAsString(getValidDto());

        ConstrainedFields fields = new ConstrainedFields(QuestionDto.class);

        mockMvc.perform(put("/api/v1/question/{questionId}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(questionDtoJson)
                .characterEncoding("utf-8"))
                .andExpect(status().isNoContent())
                .andDo(document("v1/question-put",
                        pathParameters(
                                parameterWithName("questionId").description("UUID of desired question to update")
                        ),
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("questionerAccountId").description("Account ID of the questioner"),
                                fields.withPath("pharmacyId").description("Pharmacy ID of which is going to answer the question"),
                                fields.withPath("questionText").description("The question text"),
                                fields.withPath("questionImageLinks").description("List of URIs to the question images if exists").optional()
                        )));
    }

    @Test
    void deleteQuestion() throws Exception {
        ConstrainedFields fields = new ConstrainedFields(QuestionDto.class);

        mockMvc.perform(delete("/api/v1/question/{questionId}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("v1/question-delete",
                        pathParameters(
                                parameterWithName("questionId").description("UUID of desired question to delete")
                        )));
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}