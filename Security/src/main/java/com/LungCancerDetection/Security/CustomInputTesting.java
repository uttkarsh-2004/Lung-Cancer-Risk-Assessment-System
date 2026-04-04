package com.LungCancerDetection.Security;

import com.LungCancerDetection.Security.Entity.OptionEntity;
import com.LungCancerDetection.Security.Entity.QuestionEntity;
import com.LungCancerDetection.Security.Enums.QuestionCategory;
import com.LungCancerDetection.Security.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CustomInputTesting implements CommandLineRunner {

    private final QuestionRepository questionRepo;

    @Override
    public void run(String... args) {

        if(questionRepo.count() > 0) return;

        QuestionEntity q1 = new QuestionEntity();
        q1.setQuestionText("Do you smoke cigarettes?");
        q1.setCategory(QuestionCategory.SMOKING_HISTORY);
        q1.setMaxScore(5);

        OptionEntity o1 = new OptionEntity(null,"Never smoked",0,q1);
        OptionEntity o2 = new OptionEntity(null,"Occasionally",2,q1);
        OptionEntity o3 = new OptionEntity(null,"Regular smoker",5,q1);

        q1.setOptions(List.of(o1,o2,o3));

        questionRepo.save(q1);



        QuestionEntity q2 = new QuestionEntity();
        q2.setQuestionText("For how many years have you been smoking?");
        q2.setCategory(QuestionCategory.SMOKING_HISTORY);
        q2.setMaxScore(6);
        // only if Regular smoker
        OptionEntity q2o1 = new OptionEntity(null,"Less than 5 years",2,q2);
        OptionEntity q2o2 = new OptionEntity(null,"5–15 years",4,q2);
        OptionEntity q2o3 = new OptionEntity(null,"More than 15 years",6,q2);

        q2.setOptions(List.of(q2o1,q2o2,q2o3));
        questionRepo.save(q2);

        QuestionEntity q3 = new QuestionEntity();
        q3.setQuestionText("How many cigarettes do you smoke per day?");
        q3.setCategory(QuestionCategory.SMOKING_HISTORY);
        q3.setMaxScore(6);


        OptionEntity q3o1 = new OptionEntity(null,"1-5",2,q3);
        OptionEntity q3o2 = new OptionEntity(null,"6-15",4,q3);
        OptionEntity q3o3 = new OptionEntity(null,"More than 15",6,q3);

        q3.setOptions(List.of(q3o1,q3o2,q3o3));
        questionRepo.save(q3);

        QuestionEntity q4 = new QuestionEntity();
        q4.setQuestionText("Are you exposed to second-hand smoke?");
        q4.setCategory(QuestionCategory.ENVIRONMENTAL_EXPOSURE);
        q4.setMaxScore(4);

        OptionEntity q4o1 = new OptionEntity(null,"Never",0,q4);
        OptionEntity q4o2 = new OptionEntity(null,"Sometimes",2,q4);
        OptionEntity q4o3 = new OptionEntity(null,"Frequently",4,q4);

        q4.setOptions(List.of(q4o1,q4o2,q4o3));
        questionRepo.save(q4);

        QuestionEntity q5 = new QuestionEntity();
        q5.setQuestionText("Are you regularly exposed to heavy air pollution?");
        q5.setCategory(QuestionCategory.ENVIRONMENTAL_EXPOSURE);
        q5.setMaxScore(4);

        OptionEntity q5o1 = new OptionEntity(null,"Rarely",0,q5);
        OptionEntity q5o2 = new OptionEntity(null,"Occasionally",2,q5);
        OptionEntity q5o3 = new OptionEntity(null,"Daily exposure",4,q5);

        q5.setOptions(List.of(q5o1,q5o2,q5o3));
        questionRepo.save(q5);

        QuestionEntity q6 = new QuestionEntity();
        q6.setQuestionText("Have you worked in environments with dust, asbestos or industrial chemicals?");
        q6.setCategory(QuestionCategory.ENVIRONMENTAL_EXPOSURE);
        q6.setMaxScore(5);

        OptionEntity q6o1 = new OptionEntity(null,"Never",0,q6);
        OptionEntity q6o2 = new OptionEntity(null,"Short-term exposure",2,q6);
        OptionEntity q6o3 = new OptionEntity(null,"Long-term exposure",5,q6);

        q6.setOptions(List.of(q6o1,q6o2,q6o3));
        questionRepo.save(q6);

        QuestionEntity q7 = new QuestionEntity();
        q7.setQuestionText("Do you have a cough lasting more than 3 weeks?");
        q7.setCategory(QuestionCategory.SYMPTOMS);
        q7.setMaxScore(5);

        OptionEntity q7o1 = new OptionEntity(null,"No",0,q7);
        OptionEntity q7o2 = new OptionEntity(null,"Occasionally",2,q7);
        OptionEntity q7o3 = new OptionEntity(null,"Yes persistent",5,q7);

        q7.setOptions(List.of(q7o1,q7o2,q7o3));
        questionRepo.save(q7);

        QuestionEntity q8 = new QuestionEntity();
        q8.setQuestionText("Do you experience shortness of breath?");
        q8.setCategory(QuestionCategory.SYMPTOMS);
        q8.setMaxScore(4);

        OptionEntity q8o1 = new OptionEntity(null,"Never",0,q8);
        OptionEntity q8o2 = new OptionEntity(null,"Sometimes",2,q8);
        OptionEntity q8o3 = new OptionEntity(null,"Often",4,q8);

        q8.setOptions(List.of(q8o1,q8o2,q8o3));
        questionRepo.save(q8);

        QuestionEntity q9 = new QuestionEntity();
        q9.setQuestionText("Do you feel chest pain when breathing or coughing?");
        q9.setCategory(QuestionCategory.SYMPTOMS);
        q9.setMaxScore(4);

        OptionEntity q9o1 = new OptionEntity(null,"Never",0,q9);
        OptionEntity q9o2 = new OptionEntity(null,"Occasionally",2,q9);
        OptionEntity q9o3 = new OptionEntity(null,"Frequently",4,q9);

        q9.setOptions(List.of(q9o1,q9o2,q9o3));
        questionRepo.save(q9);

        QuestionEntity q10 = new QuestionEntity();
        q10.setQuestionText("Have you experienced unexplained weight loss recently?");
        q10.setCategory(QuestionCategory.SYMPTOMS);
        q10.setMaxScore(5);

        OptionEntity q10o1 = new OptionEntity(null,"No",0,q10);
        OptionEntity q10o2 = new OptionEntity(null,"Slight weight loss",2,q10);
        OptionEntity q10o3 = new OptionEntity(null,"Significant weight loss",5,q10);

        q10.setOptions(List.of(q10o1,q10o2,q10o3));
        questionRepo.save(q10);

        QuestionEntity q11 = new QuestionEntity();
        q11.setQuestionText("Do you frequently feel fatigue or weakness?");
        q11.setCategory(QuestionCategory.SYMPTOMS);
        q11.setMaxScore(4);

        OptionEntity q11o1 = new OptionEntity(null,"Rarely",0,q11);
        OptionEntity q11o2 = new OptionEntity(null,"Sometimes",2,q11);
        OptionEntity q11o3 = new OptionEntity(null,"Often",4,q11);

        q11.setOptions(List.of(q11o1,q11o2,q11o3));
        questionRepo.save(q11);

        QuestionEntity q12 = new QuestionEntity();
        q12.setQuestionText("Do you have a history of lung disease?");
        q12.setCategory(QuestionCategory.MEDICAL_HISTORY);
        q12.setMaxScore(5);

        OptionEntity q12o1 = new OptionEntity(null,"No",0,q12);
        OptionEntity q12o2 = new OptionEntity(null,"Asthma",2,q12);
        OptionEntity q12o3 = new OptionEntity(null,"COPD or Tuberculosis",5,q12);

        q12.setOptions(List.of(q12o1,q12o2,q12o3));
        questionRepo.save(q12);

        QuestionEntity q13 = new QuestionEntity();
        q13.setQuestionText("Has anyone in your family had lung cancer?");
        q13.setCategory(QuestionCategory.MEDICAL_HISTORY);
        q13.setMaxScore(4);

        OptionEntity q13o1 = new OptionEntity(null,"No",0,q13);
        OptionEntity q13o2 = new OptionEntity(null,"Not sure",2,q13);
        OptionEntity q13o3 = new OptionEntity(null,"Yes",4,q13);

        q13.setOptions(List.of(q13o1,q13o2,q13o3));
        questionRepo.save(q13);

    }
}