package com.solution.Ongi.domain.medication;

import com.solution.Ongi.domain.meal.Meal;
import com.solution.Ongi.domain.medication.enums.MealTimeOption;
import com.solution.Ongi.domain.user.enums.AlertInterval;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealMedication {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @Enumerated(EnumType.STRING)
    private MealTimeOption mealTimeOption; // BEFORE_MEAL or AFTER_MEAL

    @ManyToMany
    @JoinTable(
        name = "meal_medication_meal",
        joinColumns = @JoinColumn(name = "meal_medication_id"),
        inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals; // 아침, 점심, 저녁 중 해당되는 meal 참조

    @Enumerated(EnumType.STRING)
    private AlertInterval alertInterval; // AFTER_30_MIN or AFTER_1_HOUR
}