package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job")
public class Job {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private OffsetDateTime scheduledTime;

    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(nullable = false)
    private String endpoint;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private Date created;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_at",nullable = false)
    @LastModifiedDate
    private Date updated;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;
}
