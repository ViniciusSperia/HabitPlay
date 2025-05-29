package com.habitplay.habit.model;

import com.habitplay.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "habits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Difficulty difficulty;

    @NotNull
    private Integer target;

    private int currentProgress = 0;

    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
