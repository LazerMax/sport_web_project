package org.example.services;

import jakarta.transaction.Transactional;
import org.example.dto.ProfileDto;
import org.example.repositories.UsersRepository;
import org.example.models.entities.Users;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final List<String> profiles = new ArrayList<>();

    public UsersService(UsersRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(Users user) {
        // Проверка на существование username перед сохранением. Важно!
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistException("Username already exists!"); // Нужно добавить этот класс исключения
        }
        allProfiles();
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифрование пароля перед сохранением
        userRepository.save(user);
    }


    public List<ProfileDto> allProfiles() {
        List<ProfileDto> profileList = new ArrayList<>();
        profileList.add(new ProfileDto("Тренер"));
        profileList.add(new ProfileDto("Студент"));
        return profileList;
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public void register(Users user) {
        //Проверка на существование username перед сохранением. Важно!
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistException("Username already exists!"); // Нужно добавить этот класс исключения
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}

