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
                .medicationTime(medication.getMedication_time())
                .isTaken(false)
                .build();

            scheduleRepository.save(schedule);
        }
    }


//     테스트용
//    @Scheduled(cron = "*/30 * * * * *") // 매 30초마다 실행
//    public void resetMedicationSchedule() {
//        log.info("💊 [스케줄러 실행] 복약 스케줄 생성 시작");
//
//        List<Medication> medications = medicationRepository.findAll();
//        LocalDate today = LocalDate.now();
//
//        for (Medication medication : medications) {
//            MedicationSchedule schedule = MedicationSchedule.builder()
//                .medication(medication)
//                .checkDate(today)
//                .isTaken(false)
//                .medicationTime(medication.getMedication_time())
//                .build();
//
//            scheduleRepository.save(schedule);
//        }
//    }


}
