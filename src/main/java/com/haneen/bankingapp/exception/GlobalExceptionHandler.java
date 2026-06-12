package com.haneen.bankingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    //handle specific exceptions - AccountException
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorDetails> handleAccountException(AccountException exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                        "ACCOUNT_NOT_FOUND"

        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //handle specific exceptions - DepositLimitExceededException
    @ExceptionHandler(DepositLimitExceededException.class)
    public ResponseEntity<ErrorDetails> handleDepositLimitExceededException(DepositLimitExceededException exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "DEPOSIT_LIMIT_EXCEEDED"

        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //handle specific exceptions - InsufficientFundsException
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorDetails> handleWithdrawInsufficientFundException(InsufficientFundsException exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "INSUFFICIENT_FUNDS"

        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //for handling validation invalid requests
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDetails> handleValidationException( MethodArgumentNotValidException exception, WebRequest webRequest){
        HashMap<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((error) -> {
                    errors.put(
                            error.getField(),
                            error.getDefaultMessage()
                    );
                });
        ValidationErrorDetails errorDetails = new ValidationErrorDetails(
                LocalDateTime.now(),
                webRequest.getDescription(false),
                "VALIDATION_ERROR",
                errors
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "SERVER_INTERNAL_ERROR"

        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
