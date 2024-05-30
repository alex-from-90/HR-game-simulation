package ru.simulator.hr.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "questionTable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "question_text")
    private String question;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @Column(name = "note")
    private String note;
}