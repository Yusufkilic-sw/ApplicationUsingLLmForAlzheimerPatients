package com.example.service;

import com.example.model.Medication;
import com.example.repository.MedicationRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.dtos.DrugDto;
import com.example.model.HealthLog;
import com.example.model.DrugIntakeLog;
import com.example.repository.DrugIntakeLogRepository;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final DrugIntakeLogRepository drugIntakeLogRepository;
    private final HealthLogService healthLogService;

    public Medication saveMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public Optional<Medication> getMedicationById(String id) {
        return medicationRepository.findById(id);
    }

    public List<Medication> getMedicationsByUserId(String userId) {
        return medicationRepository.findByUserId(userId);
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    public void deleteMedication(String id) {
        medicationRepository.deleteById(id);
    }

    public Medication markDrugAsTaken(String medicationId, int drugIndex) {
        Medication medication = medicationRepository.findById(medicationId).orElseThrow();
        DrugDto drug = medication.getDrugs().get(drugIndex);

        // Log the intake
        DrugIntakeLog intakeLog = new DrugIntakeLog();
        intakeLog.setUserId(medication.getUserId());
        intakeLog.setMedicationId(medicationId);
        intakeLog.setDrugName(drug.getName());
        intakeLog.setTakenAt(LocalDateTime.now());
        drugIntakeLogRepository.save(intakeLog);

        medicationRepository.save(medication);

        // Optionally, log to HealthLog as well
        HealthLog log = new HealthLog();
        log.setUserId(medication.getUserId());
        log.setTimestamp(LocalDateTime.now());
        log.setType("medication");
        log.setDescription("Medication taken: " + drug.getName());
        log.setDetails("Dosage: " + drug.getDosage());
        healthLogService.saveHealthLog(log);

        return medication;
    }
}