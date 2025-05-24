package com.example.service;

import com.example.model.HealthLog;
import com.example.repository.HealthLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HealthLogService {

    private final HealthLogRepository healthLogRepository;

    public HealthLog saveHealthLog(HealthLog healthLog) {
        return healthLogRepository.save(healthLog);
    }

    public List<HealthLog> getHealthLogsByUserId(String userId) {
        return healthLogRepository.findByUserId(userId);
    }

    public Optional<HealthLog> getHealthLogById(String id) {
        return healthLogRepository.findById(id);
    }

    public void deleteHealthLog(String id) {
        healthLogRepository.deleteById(id);
    }
}