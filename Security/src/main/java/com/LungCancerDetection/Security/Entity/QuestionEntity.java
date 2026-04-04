package com.LungCancerDetection.Security.Entity;

import com.LungCancerDetection.Security.Enums.QuestionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    private Integer maxScore;

    @Enumerated(EnumType.STRING)
    private QuestionCategory category;


    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<OptionEntity> options;
    private Integer sequenceOrder;

}