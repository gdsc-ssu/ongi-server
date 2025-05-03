package com.solution.Ongi.domain.meal;

import com.solution.Ongi.domain.user.User;
import com.solution.Ongi.domain.user.enums.MealType;
import com.solution.Ongi.global.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Meal extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "meal_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MealType meal_type;

    private LocalTime meal_time;

    @ManyToOne(fetch = FetchType.LAZY)//지연 로딩
    @JoinColumn(name = "user_id")
    private User user;

    //일정-달력 삭제 미반영 -> CASCADE 막기
}
