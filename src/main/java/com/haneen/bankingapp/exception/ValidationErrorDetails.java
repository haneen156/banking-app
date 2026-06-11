package com.haneen.bankingapp.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorDetails(LocalDateTime timestamp,
                                     String details,
                                     String errorCode,
                                     Map<String,String> errors) {
}
