package com.solution.Ongi.domain.medication.controller;

import com.solution.Ongi.domain.medication.Medication;
import com.solution.Ongi.domain.medication.dto.CreateMedicationRequest;
import com.solution.Ongi.domain.medication.dto.CreateMedicationResponse;
import com.solution.Ongi.domain.medication.service.MedicationService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping("/post/{userId}/medications")
    public ResponseEntity<CreateMedicationResponse> createMedication(
        Authentication authentication,
        @RequestBody CreateMedicationRequest createMedicationRequest) {

        Medication medication=medicationService.createMedication(authentication.getName(),createMedicationRequest);

        URI location= URI.create("/users/"+authentication.getName()+"/medications/"+medication.getId());

        return ResponseEntity
                .created(location)
                .body(new CreateMedicationResponse(medication.getId(),"복약이 등록되었습니다."));
    }

    @GetMapping("/users/{user_id}/medications")
    public ResponseEntity<List<Medication>> getAllMedications(@PathVariable("user_id") Long user_id) {
        List<Medication> medications = medicationService.getAllMedication(user_id);
        return ResponseEntity.ok(medications);
    }

    // Meal 삭제 엔드포인트
    @DeleteMapping("/delete/{medication_id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long medication_id) {
        medicationService.deleteMedication(medication_id);
        return ResponseEntity.noContent().build();//204 no
    }
}
