package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.models.entities.Trainer;
import org.example.repositories.TrainerRepository;
import org.example.services.CurrentUserService;
import org.example.services.TrainerService;
import org.example.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final ModelMapper mapper;
    private final CurrentUserService currentUserService;
    private final UsersService usersService;


    public void addTrainer(String trainerName) {
        Trainer trainer = new Trainer();
        trainer.setTrainerName(trainerName);
        trainerRepository.saveAndFlush(trainer);
    }

    public Trainer getTrainerByUserName(){
        UserDetails userDetails = currentUserService.getCurrentUserDetails();
        String userName = userDetails.getUsername();
        String fullName = usersService.findByUsername(userName).get().getFullName();
        return findByTrainerName(fullName);
    }

    public Trainer findByTrainerName(String trainerName) {
        return trainerRepository.findByTrainerName(trainerName)
                .orElseThrow(() -> new UsernameNotFoundException("Тренер с таким именем не найден"));
    }
}