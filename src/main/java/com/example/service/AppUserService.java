package com.example.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.example.model.AppUser;
import com.example.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUser saveUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public Optional<AppUser> getUserById(String id) {
        return appUserRepository.findById(id);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public void deleteUserById(String id) {
        appUserRepository.deleteById(id);
    }

    public Optional<AppUser> updateUser(AppUser updatedUser) {
        String id = updatedUser.getId();
        return appUserRepository.findById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            existingUser.setSurname(updatedUser.getSurname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setNameOfTheCaretaker(updatedUser.getNameOfTheCaretaker());
            return appUserRepository.save(existingUser);
        });
    }
}
