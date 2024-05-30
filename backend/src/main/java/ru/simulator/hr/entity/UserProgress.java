package ru.simulator.hr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "user_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_tel_id", referencedColumnName = "user_tel_id") // Указываем имя столбца и ссылку на user_tel_id в User
    private User user;


    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "progress_percentage")
    private double progressPercentage;

    @Column(name = "correct_answers")
    private int correctAnswers;

    @Column(name = "total_questions")
    private int totalQuestions;

    @Column(name = "wrong_answers")
    private int wrongAnswers;

    private long elapsedTime;

}