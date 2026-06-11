package com.haneen.bankingapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(@NotBlank String accountHolderName,
                                   @NotNull @Min(100) Double initialBalance) {
}
