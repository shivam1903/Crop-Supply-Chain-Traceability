
# Database Schema Documentation

This document describes the database schema used in the project, including details about each table and its columns.

---

## **ActorMaster**

Stores information about all the actors involved in the app ecosystem, ranging from farmers to manufacturers, across different crops.

| **Column Name**     | **Data Type** | **Description**                                     |
|----------------------|---------------|-----------------------------------------------------|
| `actorId`           | String (PK)   | Unique identifier for the actor.                   |
| `crop`              | String        | Crop associated with the actor.                    |
| `level`             | Integer       | Level of the actor in the process hierarchy.        |
| `process`           | String        | Process associated with the actor.                 |
| `firmName`          | String        | Name of the firm represented by the actor.         |
| `contactNumber`     | String        | Contact number of the actor.                       |
| `pocName`           | String        | Point of contact's name for the firm.              |
| `address`           | String        | Address of the actor.                              |
| `state`             | String        | State where the actor is located.                  |
| `district`          | String        | District where the actor is located.               |
| `pincode`           | Integer       | Pincode of the actor's location.                   |

---

## **FacilitationSurvey**

Maintains records of surveys conducted by company agents as part of the facilitation process.

| **Column Name**            | **Data Type** | **Description**                                        |
|-----------------------------|---------------|--------------------------------------------------------|
| `facilitationId`           | Integer (PK)  | Unique identifier for the facilitation survey.         |
| `farmerId`                 | String        | Identifier of the farmer associated with the survey.   |
| `sustainableAcre`          | Integer       | Number of sustainable acres associated with the survey.|
| `mandiId`                  | String        | Identifier of the mandi (market) in the survey.        |
| `consent`                  | String        | Consent information for the survey.                   |
| `remark`                   | String        | Remarks provided in the survey.                       |
| `aarhtiyaName`             | String        | Name of the Aarhtiya (middleman) in the survey.        |
| `aarhtiyaNumber`           | String        | Contact number of the Aarhtiya.                       |
| `harvestingDate`           | LocalDate     | Harvesting date recorded in the survey.               |
| `geographicalCoordinates`  | String        | Geographic coordinates of the surveyed location.       |
| `kaId`                     | String        | Identifier for the KA (Key Account) in the survey.     |
| `transactionDate`          | LocalDate     | Date of the transaction recorded in the survey.        |

---

## **FarmerMaster**

Captures detailed information about farmers, who are the primary stakeholders in the project.

| **Column Name**     | **Data Type** | **Description**                                     |
|----------------------|---------------|-----------------------------------------------------|
| `farmerId`          | String (PK)   | Unique identifier for the farmer.                  |
| `mobile`            | String        | Mobile number of the farmer.                       |
| `farmerName`        | String        | Name of the farmer.                                |
| `village`           | String        | Village where the farmer resides.                  |
| `enrolledAcre`      | Integer       | Number of acres enrolled by the farmer.            |
| `maxQty`            | Integer       | Maximum quantity of produce handled by the farmer. |
| `taluka`            | String        | Taluka where the farmer is located.                |
| `state`             | String        | State where the farmer resides.                    |
| `district`          | String        | District where the farmer resides.                 |
| `fatherName`        | String        | Name of the farmer's father.                       |

---

## **MandiMaster**

Serves as a reference table that provides a list of mandis (markets), derived via API based on the district.

| **Column Name**     | **Data Type** | **Description**                                     |
|----------------------|---------------|-----------------------------------------------------|
| `id`                | String (PK)   | Unique identifier for the mandi.                   |
| `name`              | String        | Name of the mandi.                                 |
| `district`          | String        | District where the mandi is located.               |
| `state`             | String        | State where the mandi is located.                  |

---

## **Password**

Stores user authentication details, where number represents the username and hash stores the hashed password. It is primarily used for verification.

| **Column Name**     | **Data Type** | **Description**                                     |
|----------------------|---------------|-----------------------------------------------------|
| `number`            | String (PK)   | Unique identifier for the password entry.          |
| `hash`              | String        | Hashed value of the password.                      |
| `level`             | Integer       | Security level associated with the password.       |

---

## **Transaction**

A critical table that logs every transaction involving the transfer of goods between actors. It includes transaction IDs and project IDs as key identifiers.

| **Column Name**            | **Data Type** | **Description**                                        |
|-----------------------------|---------------|--------------------------------------------------------|
| `transactionId`            | Integer (PK)  | Unique identifier for the transaction.                |
| `updatedDate`              | LocalDate     | Last updated date of the transaction.                 |
| `entryDate`                | LocalDate     | Entry date of the transaction.                        |
| `payment`                  | String        | Payment details for the transaction.                  |
| `transactionDate`          | LocalDate     | Date of the transaction.                              |
| `actorId`                  | String        | Identifier for the actor involved in the transaction. |
| `quantity`                 | Integer       | Quantity of the product involved in the transaction.  |
| `unit`                     | String        | Unit of measurement for the quantity.                 |
| `pricePerUnit`             | Integer       | Price per unit of the product.                        |
| `totalAmount`              | Integer       | Total amount for the transaction.                     |
| `parentNode`               | String        | Identifier for the parent node in the transaction.     |
| `comment`                  | String        | Comments related to the transaction.                  |
| `transactionDocument`      | String        | Document related to the transaction.                  |
| `geographicalCoordinates`  | String        | Geographic coordinates of the transaction.            |
| `processType`              | String        | Type of process associated with the transaction.      |
| `crop`                     | String        | Crop involved in the transaction.                     |
| `projectId`                | String        | Identifier for the project associated with the transaction. |
| `status`                   | String        | Status of the transaction.                            |
| `level`                    | Integer       | Level of the transaction.                             |

---

Feel free to reach out to shivam.agarwal@growindigo.co.in if further clarification or updates are needed!
