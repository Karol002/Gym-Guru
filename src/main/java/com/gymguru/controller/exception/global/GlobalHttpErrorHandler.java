package com.gymguru.controller.exception.global;

import com.gymguru.controller.exception.single.*;
import com.gymguru.controller.exception.single.InvalidEmailException;
import com.gymguru.controller.exception.single.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<Object> handleExerciseNotFoundException(ExerciseNotFoundException exception) {
        return new ResponseEntity<>("Exercise with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MealNotFoundException.class)
    public ResponseEntity<Object> handleMealNotFoundException(MealNotFoundException exception) {
        return new ResponseEntity<>("Meal with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<Object> handlePlanNotFoundException(PlanNotFoundException exception) {
        return new ResponseEntity<>("Plan with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<Object> handleSubscriptionNotFoundException(SubscriptionNotFoundException exception) {
        return new ResponseEntity<>("Subscription for given user id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<Object> handleTrainerNotFoundException(TrainerNotFoundException exception) {
        return new ResponseEntity<>("Trainer with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("User with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CredentialNotFoundException.class)
    public ResponseEntity<Object> handleCredentialNotFoundException(CredentialNotFoundException exception) {
        return new ResponseEntity<>("Credential not found ", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubscriptionExpiredException.class)
    public ResponseEntity<Object> handleSubscriptionExpiredException(SubscriptionExpiredException exception) {
        return new ResponseEntity<>("Subscription is expired if you want to get plan renew subscription", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PlanForUserIdNotFoundException.class)
    public ResponseEntity<Object> handlePlanUserIdNotFoundException(PlanForUserIdNotFoundException exception) {
        return new ResponseEntity<>("Plan for given user id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException exception) {
        return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleInvalidEmailException(InvalidEmailException exception) {
        return new ResponseEntity<>("Invalid email", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Object> handleEmailAlreadyExistException(EmailAlreadyExistException exception) {
        return new ResponseEntity<>("Email Already Exist ", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Object> handleInvalidCredentialException(InvalidCredentialException exception) {
        return new ResponseEntity<>("Bad password ", HttpStatus.UNAUTHORIZED);
    }
}