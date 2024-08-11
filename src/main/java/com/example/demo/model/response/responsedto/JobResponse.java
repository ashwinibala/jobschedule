package com.example.demo.model.response.responsedto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobResponse {
    private Long id;
    private String name;
    private String description;
    private OffsetDateTime scheduledTime;
    private String httpMethod;
    private String endpoint;
    private String requestBody;
    private Date created;
    private String createdBy;
    private Date updated;
    private String updatedBy;
}
