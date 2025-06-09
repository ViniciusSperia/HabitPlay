    package com.habitplay.habit.repository;

    import com.habitplay.habit.model.DefaultHabit;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;
    import java.util.UUID;

    public interface DefaultHabitRepository extends JpaRepository<DefaultHabit, UUID> {
        List<DefaultHabit> findAllByActiveTrue();
    }
