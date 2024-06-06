package com.example.codeconnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String size;

    private String processType;

    private String period;

    private String techStack;

    private LocalDate deadline;

    private String position;

    private String contactMethod;

    private String contactDetails;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;
}
