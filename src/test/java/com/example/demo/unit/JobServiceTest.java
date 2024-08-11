package com.example.demo.unit;


import com.example.demo.model.entity.Job;
import com.example.demo.repository.JobRepository;
import com.example.demo.model.requestdto.JobRequest;
import com.example.demo.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobServiceTest {
    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    private Job job;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        job = Job.builder()
                .id(1L)
                .name("Sample Job")
                .description("This is a sample job description.")
                .scheduledTime(OffsetDateTime.now())
                .httpMethod("GET")
                .endpoint("/api/sample-endpoint")
                .requestBody("Optional request body content")
                .build();
    }

    @Test
    void testCreateJob() {
        JobRequest jobRequest = JobRequest.builder()
                .name("Sample Job")
                .description("This is a sample job description.")
                .scheduledTime(OffsetDateTime.now())
                .httpMethod("GET")
                .endpoint("/api/sample-endpoint")
                .requestBody("Optional request body content")
                .build();

        when(jobRepository.save(any(Job.class))).thenReturn(job);
        Job createdJob = jobService.create(jobRequest);
        assertNotNull(createdJob);
        assertEquals("Sample Job", createdJob.getName());
        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    void testGetJobByIdWhenJobExists() {

        int jobId = 1;

        when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));

        Optional<Job> result = jobService.get(jobId);

        assertTrue(((Optional<?>) result).isPresent());
        assertEquals(jobId, result.get().getId());
        assertEquals("Sample Job", result.get().getName());
    }

    @Test
    void testGetJobByIdWhenJobDoesNotExist() {

        int jobId = 999;
        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());

        Optional<Job> result = jobService.get(jobId);

        assertTrue(result.isEmpty());
    }
}
