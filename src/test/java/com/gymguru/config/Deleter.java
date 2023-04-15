package com.gymguru.config;

import com.v1.gymguru.repository.*;
import org.springframework.stereotype.Service;

@Service
public class Deleter {
    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final MealRepository mealRepository;
    private final ExerciseRepository exerciseRepository;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;


    public Deleter(CredentialRepository credentialRepository, UserRepository userRepository, TrainerRepository trainerRepository,
                   MealRepository mealRepository, ExerciseRepository exerciseRepository, PlanRepository planRepository, SubscriptionRepository subscriptionRepository) {
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.mealRepository = mealRepository;
        this.exerciseRepository = exerciseRepository;
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void deleteFromCredentials() {
        credentialRepository.deleteAll();
    }

    public void deleteFromUsers() {
        userRepository.deleteAll();
    }

    public void deleteFromTrainers() {
        trainerRepository.deleteAll();
    }

    public  void deleteFromMeals() {
        mealRepository.deleteAll();
    }

    public void deleteFromExercises() {
        exerciseRepository.deleteAll();
    }

    public void deleteFromPlans() {
        planRepository.deleteAll();
    }

    public void deleteFromSubscriptions() {
        subscriptionRepository.deleteAll();
    }
}
