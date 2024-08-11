package com.example.demo.model.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobRequest {
    @NotBlank
    @Size(min=1, max=50)
    private String name;
    private String description;
    @NotNull
    private OffsetDateTime scheduledTime;
    @NotBlank
    private String httpMethod;
    @NotBlank
    private String endpoint;
    private String requestBody;
    private String createdBy;
    private String updatedBy;
}
