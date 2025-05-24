package com.example.controllers.usercontroller;

import com.example.model.AppUser;
import com.example.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final AppUserService appUserService;

    @PostMapping("/create")
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
        AppUser savedUser = appUserService.saveUser(appUser);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable String id) {
        Optional<AppUser> user = appUserService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    @PutMapping()
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser appUser) {
        Optional<AppUser> updatedUser = appUserService.updateUser( appUser);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        appUserService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}