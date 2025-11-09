package org.example.services.impl;

import org.example.models.entities.Users;
import org.example.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    public void createUser(Users user, String rawPassword) {
        user.setPassword(rawPassword);
        usersRepository.save(user);
    }
}
