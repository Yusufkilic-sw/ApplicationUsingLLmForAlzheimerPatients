package com.example.service;

import com.example.model.AppUser;
import com.example.model.Medication;
import com.example.dtos.DrugDto;
import com.example.repository.AppUserRepository;
import com.example.repository.MedicationRepository;
import com.example.repository.DrugIntakeLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationAdherenceScheduler {

    private final MedicationRepository medicationRepository;
    private final AppUserRepository appUserRepository;
    private final DrugIntakeLogRepository drugIntakeLogRepository;
    private final JavaMailSender mailSender;

    // Runs every day at 9 PM Istanbul time (Europe/Istanbul)
    @Scheduled(cron = "0 0 21 * * ?", zone = "Europe/Istanbul")
    public void checkMissedMedications() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        List<Medication> allMedications = medicationRepository.findAll();

        for (Medication medication : allMedications) {
            // Skip if there are no drugs required for this medication
            if (medication.getDrugs() == null || medication.getDrugs().isEmpty()) {
                continue;
            }

            AppUser user = appUserRepository.findById(medication.getUserId()).orElse(null);
            if (user == null) continue;

            String caregiverEmail = user.getEmail(); // Or use a dedicated email field

            for (DrugDto drug : medication.getDrugs()) {
                boolean takenToday = drugIntakeLogRepository.existsByUserIdAndDrugNameAndTakenAtBetween(
                        medication.getUserId(),
                        drug.getName(),
                        startOfDay,
                        endOfDay
                );
                if (!takenToday && caregiverEmail != null && !caregiverEmail.isEmpty()) {
                    sendCaregiverNotification(caregiverEmail, user, drug);
                }
            }
        }
    }

    private void sendCaregiverNotification(String caregiverEmail, AppUser user, DrugDto drug) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(caregiverEmail);
        message.setSubject("Missed Medication Alert");
        message.setText("Patient " + user.getName() + " " + user.getSurname() +
                " has not taken their medication: " + drug.getName() + " (" + drug.getDosage() + ") today.");
        mailSender.send(message);
    }
}