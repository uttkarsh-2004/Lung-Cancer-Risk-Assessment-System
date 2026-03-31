package com.LungCancerDetection.Security.Entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionText;

    private Integer score;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private QuestionEntity question;

}