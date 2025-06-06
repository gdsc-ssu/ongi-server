package com.solution.Ongi.domain.meal.service;

import com.solution.Ongi.domain.meal.dto.CreateMealRequest;
import com.solution.Ongi.domain.meal.Meal;
import com.solution.Ongi.domain.meal.repository.MealRepository;
import com.solution.Ongi.domain.user.User;
import com.solution.Ongi.domain.meal.enums.MealType;
import com.solution.Ongi.domain.user.repository.UserRepository;
import com.solution.Ongi.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MealService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final MealRepository mealRepository;
    private final DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("HH:mm");
    private final MealScheduleService mealScheduleService;

    //Meal 생성
    public Meal createMeal(String loginId, CreateMealRequest createMealRequest){
        User user=userService.getUserByLoginIdOrThrow(loginId);

        Meal meal=Meal.builder()
                .mealType(MealType.valueOf(createMealRequest.getMeal_type().toUpperCase()))
                .mealTime(LocalTime.parse(createMealRequest.getMeal_time(), timeFormatter))
                .user(user)
                .build();

        mealScheduleService.createMealSchedule(meal);

        return mealRepository.save(meal);
    }

    //유저의 Meal 전체 조회
    public List<Meal> getAllMeals(String loginId){
        User user=userService.getUserByLoginIdOrThrow(loginId);
        return mealRepository.findByUserId(user.getId());
    }

    //Meal 삭제
    public void deleteMeal(Long mealId){
        Meal meal=mealRepository.findById(mealId)
                .orElseThrow(()->new RuntimeException("식사가 존재하지 않습니다."));
        mealRepository.delete(meal);
    }
}
