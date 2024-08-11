package com.example.demo.controller;

import com.example.demo.mapper.JobMapper;
import com.example.demo.model.entity.Job;
import com.example.demo.model.requestdto.JobRequest;
import com.example.demo.model.response.responsedto.JobResponse;
import com.example.demo.service.JobService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/jobs")
@Validated
@AllArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public Page<Job> getAll(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size)  {
        return jobService.getAll(page, size);
    }

    @GetMapping("{id}")
    public ResponseEntity<JobResponse> get(@PathVariable int id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Job> job = jobService.get(id);
        return job.map(value -> new ResponseEntity<>(JobMapper.Instance.toJobResponse(value), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<JobResponse> create(@Valid @RequestBody JobRequest jobRequest) {

        Job createdJob = jobService.create(jobRequest);
        return new ResponseEntity<>(JobMapper.Instance.toJobResponse(createdJob), HttpStatus.CREATED);
    }
}
