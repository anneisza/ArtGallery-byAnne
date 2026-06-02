// src/main/java/com/galeriseni/galeri_seni/service/UserService.java
package com.galeriseni.galeri_seni.service;

import com.galeriseni.galeri_seni.entity.User;
import com.galeriseni.galeri_seni.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAllKurators() {
        return userRepository.findByRole(User.Role.KURATOR);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User createKurator(String name, String email, String rawPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email sudah terdaftar: " + email);
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(User.Role.KURATOR);
        return userRepository.save(user);
    }

    public User updateKurator(Long id, String name, String email, String rawPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan"));
        user.setName(name);
        user.setEmail(email);
        if (rawPassword != null && !rawPassword.isBlank()) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }
        return userRepository.save(user);
    }

    public void deleteKurator(Long id) {
        userRepository.deleteById(id);
    }

    public long countKurators() {
        return userRepository.countByRole(User.Role.KURATOR);
    }
}