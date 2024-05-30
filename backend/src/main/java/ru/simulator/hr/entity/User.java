package ru.simulator.hr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "user_tel_id", nullable = false) // Установка nullable = false
    private Long userTelId; // Изменили тип на Long

    @Column
    private String username;

    @Column(name = "first_login")
    private LocalDateTime firstLogin;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    // Отношение с UserProgress
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserProgress> userProgressList = new ArrayList<>();

    // Дополнительные поля, геттеры, сеттеры и т.д.
}