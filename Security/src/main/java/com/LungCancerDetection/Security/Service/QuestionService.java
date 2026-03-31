package com.LungCancerDetection.Security.Service;

import com.LungCancerDetection.Security.Dto.CreateQuestionDto;
import com.LungCancerDetection.Security.Dto.OptionResponseDto;
import com.LungCancerDetection.Security.Dto.QuestionResponseDto;
import com.LungCancerDetection.Security.Entity.OptionEntity;
import com.LungCancerDetection.Security.Entity.QuestionEntity;
import com.LungCancerDetection.Security.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<QuestionResponseDto> getAllQuestions() {

        return questionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(QuestionEntity::getId)) // or sequenceOrder
                .map(this::toDto)
                .toList();
    }

    public QuestionEntity createQuestion(CreateQuestionDto dto) {

        QuestionEntity question = new QuestionEntity();
        question.setQuestionText(dto.getQuestionText());
        question.setMaxScore(dto.getMaxScore());

        List<OptionEntity> options = dto.getOptions()
                .stream()
                .map(o -> {
                    OptionEntity option = new OptionEntity();
                    option.setOptionText(o.getOptionText());
                    option.setScore(o.getScore());
                    option.setQuestion(question);
                    return option;
                })
                .toList();

        question.setOptions(options);

        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {

        questionRepository.deleteById(id);

    }

    private QuestionResponseDto toDto(QuestionEntity question) {

        return QuestionResponseDto.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .maxScore(question.getMaxScore())
                .category(question.getCategory())
                .options(
                        question.getOptions()
                                .stream()
                                .map(option -> OptionResponseDto.builder()
                                        .id(option.getId())
                                        .optionText(option.getOptionText())
                                        .score(option.getScore()) // IMPORTANT
                                        .build()
                                )
                                .toList()
                )
                .build();
    }
}
