package com.example.controllers.medicationcontroller;

import com.example.model.Medication;
import com.example.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping("/create")
    public ResponseEntity<Medication> createMedication(@RequestBody Medication medication) {
        Medication savedMedication = medicationService.saveMedication(medication);
        return ResponseEntity.ok(savedMedication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedicationById(@PathVariable String id) {
        Optional<Medication> medication = medicationService.getMedicationById(id);
        return medication.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Medication>> getMedicationsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(medicationService.getMedicationsByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<Medication>> getAllMedications() {
        return ResponseEntity.ok(medicationService.getAllMedications());
    }

    @PutMapping()
    public ResponseEntity<Medication> updateMedication(@RequestBody Medication medication) {
        Optional<Medication> updatedMedication = medicationService.getMedicationById(medication.getId())
            .map(existing -> medicationService.saveMedication(medication));
        return updatedMedication.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable String id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }
}