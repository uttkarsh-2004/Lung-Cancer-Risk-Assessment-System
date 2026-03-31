package com.LungCancerDetection.Security.Entity;

import com.LungCancerDetection.Security.RiskLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskAssessmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private Integer totalScore;

    private Integer maxScore;

    private Double percentage;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL)
    private List<AnswerEntity> answers;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}