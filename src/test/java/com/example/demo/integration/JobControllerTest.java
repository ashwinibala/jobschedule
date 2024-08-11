package com.example.demo.integration;

import com.example.demo.controller.JobController;
import com.example.demo.model.requestdto.JobRequest;
import com.example.demo.model.response.responsedto.JobResponse;
import com.example.demo.repository.JobRepository;
import com.example.demo.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    @Autowired
    private JobRepository jobRepository;

    @AfterEach
    void tearDown() {
        jobRepository.deleteAll();
    }

    @Test
    void testCreateJobWithValidRequest() throws Exception {
        JobRequest jobRequest = JobRequest.builder()
                .name("Sample Job")
                .description("This is a sample job description.")
                .scheduledTime(OffsetDateTime.now())
                .httpMethod("GET")
                .endpoint("/api/sample-endpoint")
                .requestBody("Optional request body content")
                .build();

        mockMvc.perform(post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sample Job"))
                .andExpect(jsonPath("$.httpMethod").value("GET"))
                .andExpect(jsonPath("$.endpoint").value("/api/sample-endpoint"));
    }

    @Test
    void whenCreateJobWithBlankName_thenThrowException() throws Exception {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setName(null);
        jobRequest.setScheduledTime(OffsetDateTime.now());
        jobRequest.setHttpMethod("GET");
        jobRequest.setEndpoint("/api/sample-endpoint");
        jobRequest.setCreatedBy("super_user");
        jobRequest.setUpdatedBy("super_user");

        mockMvc.perform(post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("must not be blank"));
    }

    @Test
    void whenCreateJobWithExceedLengthName_thenThrowException() throws Exception {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setName(RandomStringUtils.randomAlphanumeric(51));
        jobRequest.setScheduledTime(OffsetDateTime.now());
        jobRequest.setHttpMethod("GET");
        jobRequest.setEndpoint("/api/sample-endpoint");
        jobRequest.setCreatedBy("super_user");
        jobRequest.setUpdatedBy("super_user");

        mockMvc.perform(post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("size must be between 1 and 50"));
    }

    @Test
    void whenCreateJobWithInvalidScheduledTime_thenThrowException() throws Exception {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setName("Valid");
        jobRequest.setScheduledTime(null);
        jobRequest.setHttpMethod("GET");
        jobRequest.setEndpoint("/api/sample-endpoint");

        mockMvc.perform(post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.scheduledTime").value("must not be null"));
    }

    @Test
    void whenCreateJobWithInvalidHttpMethod_thenThrowException() throws Exception {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setName("Valid");
        jobRequest.setScheduledTime(OffsetDateTime.now());
        jobRequest.setHttpMethod("");
        jobRequest.setEndpoint("/api/sample-endpoint");

        mockMvc.perform(post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpMethod").value("must not be blank"));
    }

    @Test
    void whenCreateJobWithInvalidEndpoint_thenThrowException() throws Exception {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setName("Valid");
        jobRequest.setScheduledTime(OffsetDateTime.now());
        jobRequest.setHttpMethod("GET");
        jobRequest.setEndpoint("");

        mockMvc.perform(post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.endpoint").value("must not be blank"));
    }

    @Test
    void whenGetJobWithValidId_thenReturnExpectedValue() throws Exception {
        // Step 1: Create a Job using POST
        JobRequest jobRequest = JobRequest.builder()
                .name("Sample Job")
                .description("This is a sample job description.")
                .scheduledTime(OffsetDateTime.now())
                .httpMethod("GET")
                .endpoint("/api/sample-endpoint")
                .requestBody("Optional request body content")
                .build();

        MvcResult postResult = mockMvc.perform(post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String responseContent = postResult.getResponse().getContentAsString();
        JobResponse jobResponse = objectMapper.readValue(responseContent, JobResponse.class);
        Long jobId = jobResponse.getId();

        mockMvc.perform(get("/api/v1/jobs/" + jobId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(jobId))
                .andExpect(jsonPath("$.name").value("Sample Job"));
    }

    @Test
    void whenGetJobWithInvalidId_thenReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/jobs/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetJobWithIdNotPresent_thenReturnNotFound() throws Exception {
        when(jobService.get(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/jobs/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}