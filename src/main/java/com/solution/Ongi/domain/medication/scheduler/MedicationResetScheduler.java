package com.solution.Ongi.domain.medication.scheduler;

import com.solution.Ongi.domain.medication.Medication;
import com.solution.Ongi.domain.medication.MedicationSchedule;
import com.solution.Ongi.domain.medication.repository.MedicationRepository;
import com.solution.Ongi.domain.medication.repository.MedicationScheduleRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MedicationResetScheduler {
    private final MedicationScheduleRepository scheduleRepository;
    private final MedicationRepository medicationRepository;

    // 매일 자정 스케줄러 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void resetMedicationSchedules() {
        List<Medication> medications = medicationRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Medication medication : medications) {
            MedicationSchedule schedule = MedicationSchedule.builder()
                .medication(medication)
                .checkDate(today)
                .isTaken(false)
                .build();

            scheduleRepository.save(schedule);
        }
    }

}
