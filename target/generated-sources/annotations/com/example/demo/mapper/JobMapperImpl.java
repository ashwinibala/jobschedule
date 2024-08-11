package com.example.demo.mapper;

import com.example.demo.model.entity.Job;
import com.example.demo.model.requestdto.JobRequest;
import com.example.demo.model.response.responsedto.JobResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-11T02:40:39-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class JobMapperImpl implements JobMapper {

    @Override
    public JobResponse toJobResponse(Job job) {
        if ( job == null ) {
            return null;
        }

        JobResponse jobResponse = new JobResponse();

        jobResponse.setId( job.getId() );
        jobResponse.setName( job.getName() );
        jobResponse.setDescription( job.getDescription() );
        jobResponse.setScheduledTime( job.getScheduledTime() );
        jobResponse.setHttpMethod( job.getHttpMethod() );
        jobResponse.setEndpoint( job.getEndpoint() );
        jobResponse.setRequestBody( job.getRequestBody() );
        jobResponse.setCreated( job.getCreated() );
        jobResponse.setCreatedBy( job.getCreatedBy() );
        jobResponse.setUpdated( job.getUpdated() );
        jobResponse.setUpdatedBy( job.getUpdatedBy() );

        return jobResponse;
    }

    @Override
    public Job toJob(JobRequest jobRequest) {
        if ( jobRequest == null ) {
            return null;
        }

        Job.JobBuilder job = Job.builder();

        job.name( jobRequest.getName() );
        job.description( jobRequest.getDescription() );
        job.scheduledTime( jobRequest.getScheduledTime() );
        job.httpMethod( jobRequest.getHttpMethod() );
        job.endpoint( jobRequest.getEndpoint() );
        job.requestBody( jobRequest.getRequestBody() );
        job.createdBy( jobRequest.getCreatedBy() );
        job.updatedBy( jobRequest.getUpdatedBy() );

        return job.build();
    }
}
