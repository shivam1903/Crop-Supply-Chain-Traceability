package com.example.GI.Backend.Controller;

import com.example.GI.Backend.model.Transaction;
import com.example.GI.Backend.Service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/view")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);


    @GetMapping("/node_to_node_actor")
    public ResponseEntity<List<Map<String, Object>>> getDetailsforNode(@RequestParam String project_id) {
        try {
            logger.info("Fetching details for project ID: {}", project_id);
            List<Map<String, Object>> final_result = transactionService.getFarmerDetailsforNode(project_id);
            List<Map<String, Object>> actor_details = transactionService.getActorDetailsforNode(project_id);
            final_result.addAll(actor_details);
            logger.info("Details found for project ID {}: {}", project_id, final_result);

            return ResponseEntity.ok(final_result);
        } catch (Exception e) {
            logger.error("Error while fetching transactions for project ID: {}", project_id, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/node_to_node_transaction")
    public ResponseEntity<List<Map<String, Object>>> getTransactionsForNode(@RequestParam String project_id){
        try {
            logger.info("Fetching transactions for project ID: {}", project_id);
            List<Map<String, Object>> final_result = transactionService.getTransactionsForNode(project_id);
            logger.info("Transactions found for project ID {}: {}", project_id, final_result);
            return ResponseEntity.ok(final_result);
        } catch (Exception e) {
            logger.error("Error while fetching transactions for project ID: {}", project_id, e);
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/product_line/")
    public ResponseEntity<List<Map<String, Object>>> getDetailsforProduct(@RequestParam String projectId) {
        try {
            logger.info("Fetching details for project ID: {}", projectId);

            List<String> actorIds = transactionService.getActorsByProjectId(projectId);
            System.out.println("This was successfull");
            System.out.println(actorIds.toString());
            List<Map<String, Object>> output = transactionService.getActorDetails(actorIds, projectId);
//            for(String actorId: actorIds){
//                System.out.println(actorId);
//                output = transactionService.getActorDetails(actorId);
//            }
            logger.info("Actor Details found for project ID {}: {}", projectId, output);

            return ResponseEntity.ok(output);
        } catch (Exception e) {
            logger.error("Error while fetching transactions for project ID: {}", projectId, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/geographical_view/{projectId}")
    public ResponseEntity<List<Map<String, Object>>> getDetailsforGeographical(@PathVariable String projectId) {
        try {
            logger.info("Fetching geographical values for project ID: {}", projectId);

            List<Map<String, Object>> transactions = transactionService.getDataForGeographicalView(projectId);
            logger.info("Geographical Details found for project ID {}: {}", projectId, transactions);

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Error while fetching transactions for project ID: {}", projectId, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/node_to_node_actor_list")
    public ResponseEntity<List<Map<String, List<String>>>> getNodeActorList(@RequestParam String project_id){
        try {
            logger.info("Fetching geographical values for project ID: {}", project_id);

            List<Map<String, List<String>>> transactions = transactionService.getNodeActorList(project_id);
            logger.info("Geographical Details found for project ID {}: {}", project_id, transactions);

            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Error while fetching transactions for project ID: {}", project_id, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/node_to_node_connection_list")
    public ResponseEntity<Map<String, List<String>>> getParentNodeActors(@RequestParam String project_id) {
        try {
            Map<String, List<String>> result = transactionService.getChildActorsGroupedByParent(project_id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error fetching parent node actors for project_id: {}", project_id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/node_transaction_summary")
    public ResponseEntity<Map<String, Object>> getNodeTransactionSummary(
            @RequestParam String parent_node,
            @RequestParam Integer actor_id,
            @RequestParam String project_id) {
        try {
            Map<String, Object> summary = transactionService.getNodeTransactionSummary(parent_node, actor_id, project_id);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            logger.error("Error fetching transaction summary", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/node_transaction_details")
    public ResponseEntity<List<Map<String, Object>>> getNodeTransactionDetails(
            @RequestParam String parent_node,
            @RequestParam Integer actor_id,
            @RequestParam String project_id) {
        try {
            List<Map<String, Object>> details = transactionService.getNodeTransactionDetails(parent_node, actor_id, project_id);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            logger.error("Error fetching transaction details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/farmer_details")
    public ResponseEntity<Map<String, Object>> getFarmerDetails(@RequestParam String farmer_id) {
        try {
            Map<String, Object> farmerDetails = transactionService.getFarmerDetails(farmer_id);
            if (farmerDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(farmerDetails);
        } catch (Exception e) {
            logger.error("Error fetching farmer details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/actor_details")
    public ResponseEntity<?> getActorDetails(
            @RequestParam(required = true) String projectId,
            @RequestParam(required = true) Integer actorId) {
        try {
            if (projectId == null || projectId.trim().isEmpty() || actorId == null) {
                return ResponseEntity
                        .badRequest()
                        .body(Collections.singletonMap("error", "projectId and actorId must be provided."));
            }

            Map<String, Object> actorDetails = transactionService.getActorDetails(projectId, actorId);
            if (actorDetails == null || actorDetails.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "No actor found with the given projectId and actorId."));
            }

            return ResponseEntity.ok(actorDetails);
        } catch (Exception e) {
            // Optional: log the error using a logger
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An unexpected error occurred: " + e.getMessage()));
        }
    }
    // post mapping for product view master
    @PostMapping("/save_product_view_json")
    public ResponseEntity<Map<String, Object>> createProductViewMaster(@RequestBody Map<String, Object> productViewMaster, @RequestParam String project_id) {
        try {
            Map<String, Object> result = transactionService.createProductViewMaster(productViewMaster, project_id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // logger.error("Error creating product view master", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get_product_view_json")
    public ResponseEntity<Map<String, Object>> getProductViewJson(@RequestParam String project_id) {
        try {
            Map<String, Object> result = transactionService.getProductViewJson(project_id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // logger.error("Error getting product view json", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getGeographicalViewActors")
    public ResponseEntity<List<Map<String, Object>>> getActorsAndFarmers(@RequestParam String project_id) {
        try {
            List<Map<String, Object>> response = transactionService.getActorsAndFarmersforGeographical(project_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/journey_api_data")
    public ResponseEntity<List<Map<String, Object>>> getProjectLevelDetails(@RequestParam String project_id) {
        try {
            List<Map<String, Object>> result = transactionService.getProjectLevelDetails(project_id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    


}
