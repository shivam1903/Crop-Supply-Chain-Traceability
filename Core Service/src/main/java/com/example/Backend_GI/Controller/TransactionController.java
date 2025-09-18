package com.example.Backend_GI.Controller;

import com.example.Backend_GI.model.ActorMaster;
import com.example.Backend_GI.model.InitialTransaction;
import com.example.Backend_GI.service.CacheService;
import com.example.Backend_GI.service.FileStorageService;
import com.example.Backend_GI.service.S3Service;
import com.example.Backend_GI.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private S3Service s3Service;

    @GetMapping("/checking")
    public ResponseEntity<String> checkOutput(){
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody InitialTransaction transaction) {
        try {
            System.out.println("Received request to save transaction: {} " + transaction.toString());
            logger.info("Received request to save transaction: {}", transaction);

            cacheService.clearCache();
            if (transaction.getPayment() == null || transaction.getTransaction_document() == null) {
                transaction.setStatus("Incomplete");
            } else {
                transaction.setStatus("Complete");
            }

            Integer transactionId = transactionService.saveTransaction(transaction);
            logger.info("Transaction saved successfully with ID: {}", transactionId);

            return ResponseEntity.ok("Transaction saved successfully with ID: " + transactionId);
        } catch (Exception e) {
            logger.error("Error while saving transaction: {}", transaction, e);
            return ResponseEntity.status(500).body("Error while saving transaction: " + e.getMessage());
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<InitialTransaction> getTransactionDetails(@PathVariable int transactionId) {
        try {
            logger.info("Fetching details for transaction ID: {}", transactionId);

            Optional<InitialTransaction> transactionDetails = transactionService.getTransactionDetails(transactionId);
            if (transactionDetails.isPresent()) {
                logger.info("Transaction details found: {}", transactionDetails.get());
                InitialTransaction t1 = transactionDetails.get();
                if(t1.getTransaction_document() != null && !t1.getTransaction_document().isEmpty()){
                    String base64 = s3Service.getFileAsBase64(t1.getTransaction_document());
                    t1.setTransaction_document(base64);
                }
                if(t1.getPayment() != null && !t1.getPayment().isEmpty()){
                    String base64 = s3Service.getFileAsBase64(t1.getPayment());
                    t1.setPayment(base64);
                }
                return ResponseEntity.ok(transactionDetails.get());
            } else {
                logger.warn("Transaction not found for ID: {}", transactionId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error while fetching transaction details for ID: {}", transactionId, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/actor/{actorId}")
    public ResponseEntity<List<Map<String, Object>>> getTransactionsByActorId(@PathVariable Integer actorId) {
        try {
            logger.info("Fetching transactions for actor ID: {}", actorId);

            List<Map<String, Object>> transactions = transactionService.getTransactionsByActorId(actorId);
            logger.info("Transactions found for actor ID {}: {}", actorId, transactions);

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Error while fetching transactions for actor ID: {}", actorId, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping(value = "/updatesingle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateTransaction(@RequestPart("payload") String payloadJson,
                                                    @RequestPart("transactionDocument") MultipartFile file1){
        try {
            logger.info("Received request to update transaction with payload: {}", payloadJson);

            Map<String, Object> payload = new ObjectMapper().readValue(payloadJson, Map.class);
            Integer transactionId = (Integer) payload.get("transactionId");

            FileStorageService fileStorageService = new FileStorageService();

            String transactionDocument = s3Service.uploadFile(file1.getOriginalFilename(), file1.getBytes());
//            String payment = s3Service.uploadFile(file2.getOriginalFilename(), file2.getBytes());

//            String transactionDocument = "hellp";
//            String payment = "placeholder";

            String comment = (String) payload.getOrDefault("comment", "");
            String status = transactionDocument.isBlank() ? "Incomplete" : "Complete";
            boolean problemreported = (boolean) payload.get("problemReported");
            String problemcomment = (String) payload.get("problemComment");

            String result = transactionService.updateTransaction(transactionId, transactionDocument,null, comment, status, problemreported, problemcomment );
            logger.info("Transaction updated successfully with ID: {}", transactionId);

            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request to update transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while updating transaction", e);
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PutMapping(value = "/updateboth", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateTransaction(@RequestPart("payload") String payloadJson,
                                                    @RequestPart("transactionDocument") MultipartFile file1,
                                                    @RequestPart("payment") MultipartFile file2) {
        try {
            logger.info("Received request to update transaction with payload: {}", payloadJson);

            Map<String, Object> payload = new ObjectMapper().readValue(payloadJson, Map.class);
            Integer transactionId = (Integer) payload.get("transactionId");

            FileStorageService fileStorageService = new FileStorageService();

            String transactionDocument = s3Service.uploadFile(file1.getOriginalFilename(), file1.getBytes());
            String payment = s3Service.uploadFile(file2.getOriginalFilename(), file2.getBytes());

//            String transactionDocument = "hellp";
//            String payment = "placeholder";

            String comment = (String) payload.getOrDefault("comment", "");
            String status = transactionDocument.isBlank() || payment.isBlank() ? "Incomplete" : "Complete";
            boolean problemreported = (boolean) payload.get("problemReported");
            String problemcomment = (String) payload.get("problemComment");

            String result = transactionService.updateTransaction(transactionId, transactionDocument, payment, comment, status, problemreported, problemcomment );
            logger.info("Transaction updated successfully with ID: {}", transactionId);

            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request to update transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while updating transaction", e);
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping(value = "/gclevel/{projectId}")
    public ResponseEntity<List<Map<String, Object>>> getCoorAndLevelbyProj(@PathVariable String projectId) {
        try {
            logger.info("Fetching coordinator and level details for project ID: {}", projectId);

            List<Map<String, Object>> transactions = transactionService.getCoorAndLevelbyProj(projectId);
            logger.info("Coordinator and level details for project ID {}: {}", projectId, transactions);

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Error while fetching coordinator and level details for project ID: {}", projectId, e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
