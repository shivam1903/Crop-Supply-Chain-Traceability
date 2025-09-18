# Repository Layer Documentation

## Overview
The repository layer contains interfaces that interact directly with the database. These interfaces extend `JpaRepository` to provide CRUD operations and custom query methods for specific use cases.

---

## Repositories

### 1. **TransactionRepository**
Handles database operations related to transactions.

| **Method**                     | **Description**                                                                                       |
|--------------------------------|-------------------------------------------------------------------------------------------------------|
| `findTransactionsByActorId`    | Retrieves a list of transactions for a specific `actorId`.                                            |
| `updateTransactionDetails`     | Updates transaction details, including `transactionDocument`, `payment`, `comment`, and `status`.      |
| `findGCandLevel`               | Fetches geographical coordinates and levels for a specific `projectId`.                               |

---

### 2. **PasswordRepository**
Manages database queries related to user credentials.

| **Method**       | **Description**                                                                                       |
|------------------|-------------------------------------------------------------------------------------------------------|
| `findByNumber`   | Retrieves the password hash and level for a user identified by `number`.                               |

---

### 3. **MandiMasterRepository**
Handles queries for mandi (market) data.

| **Method**              | **Description**                                                                                       |
|-------------------------|-------------------------------------------------------------------------------------------------------|
| `findMandibyDistrict`   | Retrieves a list of mandis (name and ID) for a given district.                                        |

---

### 4. **FarmerMasterRepository**
Provides CRUD operations for farmer-related data.

| **Method**             | **Description**                                                                                       |
|------------------------|-------------------------------------------------------------------------------------------------------|
| **(Inherited)**        | All basic CRUD operations are inherited from `JpaRepository`.                                         |

---

### 5. **FacilitationSurveyRepository**
Provides CRUD operations for facilitation surveys.

| **Method**             | **Description**                                                                                       |
|------------------------|-------------------------------------------------------------------------------------------------------|
| **(Inherited)**        | All basic CRUD operations are inherited from `JpaRepository`.                                         |

---

### 6. **ActorMasterRepository**
Handles queries related to actors and their details.

| **Method**                   | **Description**                                                                                       |
|------------------------------|-------------------------------------------------------------------------------------------------------|
| `findActorsByCropAndLevel`   | Retrieves actors filtered by crop type and process level.                                             |
| `findLevelbyNumber`          | Fetches the process level of an actor identified by their contact number.                             |
| `findDetailsbyNumber`        | Retrieves detailed information of an actor using their contact number.                                |

---

## Additional Notes

- **Native Queries**: Many methods utilize `@Query` to define native SQL queries for optimized database operations.
- **Transactional Support**: Methods annotated with `@Modifying` allow updates to the database, ensuring changes are persisted.
- **JpaRepository Features**: All repositories inherit basic CRUD methods (e.g., `save`, `findById`, `delete`) from `JpaRepository`.
- **Optional Handling**: Methods like `findDetailsbyNumber` return `Optional` objects to handle cases where data may not exist.

---
