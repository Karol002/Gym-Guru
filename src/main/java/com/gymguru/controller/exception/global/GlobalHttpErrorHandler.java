package com.gymguru.controller.exception.global;

import com.gymguru.controller.exception.single.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<Object> handleExerciseNotFoundException(ExerciseNotFoundException exception) {
        return new ResponseEntity<>("Exercise with given id dosen't exist", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MealNotFoundException.class)
    public ResponseEntity<Object> handleMealNotFoundException(MealNotFoundException exception) {
        return new ResponseEntity<>("Meal with given id doesn't exist", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<Object> handlePersonNotFoundException(PersonNotFoundException exception) {
        return new ResponseEntity<>("Person with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<Object> handlePlanNotFoundException(PlanNotFoundException exception) {
        return new ResponseEntity<>("Plan with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<Object> handleSubscriptionNotFoundException(SubscriptionNotFoundException exception) {
        return new ResponseEntity<>("Subscription with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<Object> handleTrainerNotFoundException(TrainerNotFoundException exception) {
        return new ResponseEntity<>("Trainer with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("User with given id doesn't exist", HttpStatus.NOT_FOUND);
    }
}
