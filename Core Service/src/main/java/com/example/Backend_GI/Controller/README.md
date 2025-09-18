# Controller Layer Documentation

## Overview
The controller layer contains REST API endpoints that handle incoming HTTP requests and provide appropriate responses. Each controller is responsible for specific functionalities and interacts with the service layer to process data.

---

## Controllers

### 1. **ActorMasterController**
Handles API endpoints related to actors.

| **Endpoint**         | **Method** | **Description**                                                                                   |
|-----------------------|------------|---------------------------------------------------------------------------------------------------|
| `/api/actor/save`     | POST       | Save details of an actor, such as crop type and process level.                                   |
| `/api/actor/{actorId}`| GET        | Retrieve details of an actor using their unique `actorId`.                                       |
| `/api/actor/find/croplevel` | GET  | Retrieve actors filtered by crop type and level using the provided payload.                      |
| `/api/actor/level/{number}`| GET   | Fetch the process level associated with a specific `number`.                                     |
| `/api/actor/details/{number}`| GET | Retrieve detailed information about an actor using their unique `number`.                       |

---

### 2. **FacilitationSurveyController**
Handles API endpoints for facilitation surveys.

| **Endpoint**                   | **Method** | **Description**                                                                                   |
|--------------------------------|------------|---------------------------------------------------------------------------------------------------|
| `/api/facilitation/save`       | POST       | Save a facilitation survey conducted by company agents.                                           |
| `/api/facilitation/{facilitationId}` | GET   | Retrieve details of a facilitation survey using its unique `facilitationId`.                     |

---

### 3. **FarmerMasterController**
Handles API endpoints for farmer-related data.

| **Endpoint**              | **Method** | **Description**                                                                                   |
|---------------------------|------------|---------------------------------------------------------------------------------------------------|
| `/api/farmer/{farmerId}`  | GET        | Retrieve detailed information about a farmer using their unique `farmerId`.                      |

---

### 4. **MandiMasterController**
Handles API endpoints for mandis (markets).

| **Endpoint**              | **Method** | **Description**                                                                                   |
|---------------------------|------------|---------------------------------------------------------------------------------------------------|
| `/api/mandi/{district}`   | GET        | Retrieve a list of mandis within a specific district.                                             |

---

### 5. **PasswordController**
Handles API endpoints for user verification.

| **Endpoint**               | **Method** | **Description**                                                                                   |
|----------------------------|------------|---------------------------------------------------------------------------------------------------|
| `/api/verify/v1`           | POST       | Verify user credentials using `number` (username) and `hash` (hashed password). Returns the user's access level if valid. |

---

### 6. **TransactionController**
Handles API endpoints for transactions.

| **Endpoint**                     | **Method** | **Description**                                                                                   |
|----------------------------------|------------|---------------------------------------------------------------------------------------------------|
| `/api/transactions/save`         | POST       | Save a new transaction, including details such as actor ID, quantity, and price per unit.         |
| `/api/transactions/{transactionId}` | GET     | Retrieve detailed information about a transaction using its unique `transactionId`.               |
| `/api/transactions/actor/{actorId}`| GET      | Fetch all transactions associated with a specific actor (`actorId`).                              |
| `/api/transactions/update`       | PUT       | Update an existing transaction with new details and files for `transactionDocument` and `payment`. |
| `/api/transactions/gclevel/{projectId}` | GET  | Retrieve geographical coordinates and levels for a specific `projectId`.                         |

---

## Additional Notes

- **HTTP Methods**:
    - `POST`: For creating new records or submitting data.
    - `GET`: For retrieving data based on unique identifiers or query parameters.
    - `PUT`: For updating existing records.

- **Error Handling**:
    - Controllers return appropriate HTTP status codes (e.g., `200 OK`, `404 Not Found`, `400 Bad Request`, `500 Internal Server Error`) based on the outcome of requests.

- **Inter-Service Communication**:
    - Controllers delegate business logic to the respective service classes, ensuring a clear separation of concerns.

- **Payloads and Responses**:
    - Endpoints like `/save` and `/update` expect well-structured JSON payloads and multipart files where applicable.
    - Responses include meaningful messages or data for successful operations.

---
