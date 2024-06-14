package com.example.codeconnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "process_type")
    private String processType;

    private String period;

    @ElementCollection(targetClass = TechStack.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "post_tech_stack", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tech_stack")
    private List<TechStack> techStack;

    private LocalDate deadline;

    @ElementCollection(targetClass = Position.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "post_position", joinColumns = @JoinColumn(name = "post_id"))
    private List<Position> position;

    @Column(name = "contact_method")
    private String contactMethod;

    @Column(name = "contact_details")
    private String contactDetails;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;
}
