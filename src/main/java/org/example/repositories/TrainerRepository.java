package org.example.repositories;

import org.example.models.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, String> {
    Optional<Trainer> findByTrainerName(String trainerName);

    @Override
    void deleteById(String id);
}