
# Service Layer Documentation

## Overview
The service layer contains the business logic for the backend application. Each service is designed to handle specific responsibilities, interacting with repositories for database operations and providing processed data to the controllers.

---

## Services

### 1. **TransactionService**
Handles the business logic for managing transactions.

| **Method**                  | **Description**                                                                                       |
|-----------------------------|-------------------------------------------------------------------------------------------------------|
| `saveTransaction`           | Saves a new transaction, calculates `totalAmount`, and sets `entryDate` and `updatedDate`.           |
| `getTransactionDetails`     | Retrieves transaction details by `transactionId`.                                                    |
| `getTransactionsByActorId`  | Fetches all transactions associated with a specific actor (`actorId`).                               |
| `getCoorAndLevelbyProj`     | Retrieves geographical coordinates and levels for a given project ID.                                |
| `updateTransaction`         | Updates an existing transaction with new details and files for `transactionDocument` and `payment`.  |

---

### 2. **PasswordService**
Handles user verification and password validation.

| **Method**       | **Description**                                                                                       |
|------------------|-------------------------------------------------------------------------------------------------------|
| `verifyUser`     | Verifies user credentials (`number` and `hash`) and returns the user's access level if valid.          |

---

### 3. **MandiMasterService**
Provides data for mandis (markets).

| **Method**          | **Description**                                                                                       |
|---------------------|-------------------------------------------------------------------------------------------------------|
| `getMandibyDistrict`| Retrieves a list of mandis by district, including mandi names and IDs.                                 |

---

### 4. **FarmerMasterService**
Manages farmer-related data.

| **Method**            | **Description**                                                                                       |
|-----------------------|-------------------------------------------------------------------------------------------------------|
| `getFarmerDetails`    | Retrieves detailed information of a farmer using their `farmerId`.                                    |

---

### 5. **FacilitationSurveyService**
Handles the business logic for facilitation surveys.

| **Method**          | **Description**                                                                                       |
|---------------------|-------------------------------------------------------------------------------------------------------|
| `saveSurvey`        | Saves a new facilitation survey and sets the current `transactionDate`.                                |
| `getSurveyDetails`  | Retrieves survey details by `facilitationId`.                                                         |

---

### 6. **ActorMasterService**
Manages actor-related data, including their roles and levels.

| **Method**                   | **Description**                                                                                       |
|------------------------------|-------------------------------------------------------------------------------------------------------|
| `saveActorDetails`           | Saves actor details, including crop type and process level.                                           |
| `getActorDetails`            | Retrieves actor details using `actorId`.                                                             |
| `getLevelbyNumber`           | Fetches the process level for a given actor identified by `number`.                                   |
| `getDetailsbyNumber`         | Retrieves detailed information for an actor using their `number`.                                     |
| `getActorsByCropAndLevel`    | Fetches a list of actors filtered by crop type and adjusted process level.                            |

---

## Additional Notes

- **Transactional Methods**: Methods like `saveTransaction` and `saveSurvey` use `@Transactional` to ensure atomic operations.
- **Error Handling**: Service methods handle exceptions and return meaningful errors when data is invalid or unavailable.
- **Stream Processing**: Many methods use Java Streams to transform raw query results into structured data formats.
- **Caching**: Services like `TransactionService` and `PasswordService` can leverage caching for performance optimization.

---