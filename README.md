## Core Features
- Create a bank account
- Retrieve account details by ID
- Retrieve all accounts
- Update account information
- Delete an account
- Transaction Operations
- Deposit money into an account
- Withdraw money from an account
- Request DTO Refactoring

--- 
## Request models:

- CreateAccountRequest
- DepositRequest
- WithdrawRequest

## Response models 
- AccountDto

---
## Request validation using Jakarta Validation:

- Account Creation
- Account holder name cannot be blank
- Initial balance cannot be null
- Initial balance must be at least 100
- Deposits
- Amount cannot be null
- Amount must be positive
- Withdrawals
- Amount cannot be null
- Amount must be positive
- Business Rule Validation
- Insufficient Funds Protection
---
## Global Exception Handling

### Custom exceptions for:

- Account not found
- Insufficient funds
- Deposit limit exceeded
- Request validation failures
- Unexpected server errors
- Structured Error Responses

### Validation failures return field-level errors:

{
    "timestamp": "...",
    "details": "uri=/accounts",
    "errorCode": "VALIDATION_ERROR",
    "errors": {
        "initialBalance": "must be greater than or equal to 100"
    }
}

### Business exceptions return consistent error responses:

{
    "timestamp": "...",
    "message": "Insufficient funds",
    "details": "uri=/accounts/1/withdraw",
    "errorCode": "INSUFFICIENT_FUNDS"
}

---
## API Documentation

- Swagger/OpenAPI for interactive API documentation and testing.
---
## Monitoring
- Spring Boot Actuator for application monitoring and operational endpoints.
