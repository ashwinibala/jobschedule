package com.example.demo.service;

import com.example.demo.mapper.JobMapper;
import com.example.demo.model.entity.Job;
import com.example.demo.repository.JobRepository;
import com.example.demo.model.requestdto.JobRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Page<Job> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobRepository.findAll(pageable);
    }

    public Optional<Job> get(int id) {
        return jobRepository.findById(id);
    }

    public Job create(JobRequest jobRequest) {
        Job createJob = JobMapper.Instance.toJob(jobRequest);
        return jobRepository.save(createJob);
    }
}
