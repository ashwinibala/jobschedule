package com.example.demo.mapper;

import com.example.demo.model.entity.Job;
import com.example.demo.model.requestdto.JobRequest;
import com.example.demo.model.response.responsedto.JobResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobMapper {

    JobMapper Instance = Mappers.getMapper(JobMapper.class);

    JobResponse toJobResponse(Job job);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    Job toJob(JobRequest jobRequest);
}
