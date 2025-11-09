package org.example.services;

import org.example.models.entities.Trainer;

public interface TrainerService {
    void addTrainer(String trainerName);
    Trainer findByTrainerName(String trainerName);
    public Trainer getTrainerByUserName ();
}

