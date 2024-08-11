package com.example.demo.repository;

import com.example.demo.model.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends CrudRepository<Job, Long> {


    Page<Job> findAll(Pageable pageable);

    Optional<Job> findById(int id);

}
