package com.example.GI.Backend.Service;

import com.example.GI.Backend.Repository.ActorMasterRepository;
import com.example.GI.Backend.Repository.FarmerMasterRepository;
import com.example.GI.Backend.Repository.FarmerProjectMasterRepository;
import com.example.GI.Backend.model.ActorMaster;
import com.example.GI.Backend.model.FarmerMaster;
import com.example.GI.Backend.model.Transaction;
import com.example.GI.Backend.Repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.GI.Backend.Repository.ProductViewMasterRepository;
import com.example.GI.Backend.model.ProductViewMaster;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ActorMasterRepository actorMasterRepository;

    @Autowired
    private FarmerMasterRepository farmerMasterRepository;

    @Autowired
    private FarmerProjectMasterRepository farmerProjectMasterRepository;

    @Autowired
    private ProductViewMasterRepository productViewMasterRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Map<String, Object>> getTransactionsForNode(String projectId) {
        List<Object[]> results = transactionRepository.findTransactionsForNode(projectId);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("parent_id", result[0]);
            map.put("actor_id", ((Number) result[1]).intValue());
            map.put("blockchain_hash", result[2]);
            map.put("transaction_id", result[3]);
            map.put("process_type", result[4]);
            map.put("transaction_date", result[5]);
            map.put("total_quantity", result[6] == null ? 0 : ((Number) result[6]).intValue());
            map.put("total_amount", result[7] == null ? 0 : ((Number) result[7]).intValue());
            if(result[8] != null && !((String) result[8]).isEmpty()){
                String base64 = null;
                try {
                    base64 = s3Service.getFileAsBase64((String) result[8]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                map.put("transaction_document", base64);
            }
            if(result[9] != null && !((String) result[9]).isEmpty()){
                String base64 = null;
                try {
                    base64 = s3Service.getFileAsBase64((String) result[9]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                map.put("payment", base64);
            }
            return map;
        }).collect(Collectors.toList());
    }

    //    "blockchain_hash": "abc123",
//            "transaction_id": "TXN001",
//            "process_type": "Cotton Sale",
//            "transaction_date": "2025-03-24",
//            "total_quantity": "5000 kg",
//            "total_amount": "$10,000",
//            "transaction_document": "invoice_1.pdf",
//            "payment_receipt": "receipt_1.pdf"

    public List<Map<String, Object>> getActorDetailsforNode(String projectId) {
        return actorMasterRepository.findActorDetailsforNode(projectId);
    }

    public List<Map<String, Object>> getFarmerDetailsforNode(String projectId){
        List<String> farmer_ids = farmerProjectMasterRepository.findFarmerIDsbyProject(projectId);

        if (farmer_ids.isEmpty()) {
            return Collections.emptyList();
        }

        // Fetch farmer details using the retrieved farmer IDs
        List<Object[]> results = farmerMasterRepository.findFarmerDetailsforNode(farmer_ids);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("actor_id", result[0] ); // Safely cast to Integer
            map.put("firm_name",  result[1]);
            map.put("contact_number", result[2]);
            map.put("address", result[3] + ", " + result[4] + ", " + result[5] + ", " + result[6]);
            map.put("process", "Farming");
            return map;
        }).collect(Collectors.toList());
    }




    public List<String> getActorsByProjectId(String projectId) {
        return transactionRepository.findActorsByProjectId(projectId);
    }

    public List<Map<String, Object>> getActorDetails(List<String> actorIds, String projectId) {

        List<Map<String, Object>> actorDetailsList = new ArrayList<>();

        for (String actorId : actorIds) {
            System.out.println("this is actorId: " + actorId + " this is projectId: " + projectId);
            Map<String, Object> actorDetails = (Map<String, Object>) actorMasterRepository.findActorDetails(actorId, projectId);
            if (actorDetails != null && !actorDetails.isEmpty()) {
                actorDetailsList.add(actorDetails);
            }
        }

        return actorDetailsList;
    }

    public List<Map<String, Object>> getDataForGeographicalView(String projectId) {
        List<Object[]> results = transactionRepository.findTransactionsForGeographical(projectId);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("transactionId", ((Number) result[0]).intValue());
            map.put("geographicalCoordinates", result[1]);
            map.put("processType", result[2]);
            map.put("crop", result[3]);
            String firm_name = actorMasterRepository.findFirmName((String) result[4], projectId);
            map.put("firm_name", firm_name);
            map.put("level", ((Number) result[5]).intValue());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, List<String>>> getNodeActorList(String projectId) {
        Map<String, List<String>> result = new HashMap<>();

        // 1. Get farmer IDs
        List<String> farmerIds = farmerProjectMasterRepository.findFarmerIdsByProjectId(projectId);
        result.put("farmer", farmerIds);

        // 2. Get other process_type and actor_id mappings
        List<Object[]> rawData = transactionRepository.findProcessTypeAndActorIdsByProjectId(projectId);

        Map<String, Set<String>> tempMap = new HashMap<>();

        for (Object[] row : rawData) {
            String processType = (String) row[0];
            Integer actorId = (Integer) row[1];

            if (processType == null || actorId == null || processType.equalsIgnoreCase("farmer")) {
                continue;
            }

            tempMap.computeIfAbsent(processType, k -> new HashSet<>()).add(String.valueOf(actorId));
        }

        // Convert Set to List
        for (Map.Entry<String, Set<String>> entry : tempMap.entrySet()) {
            result.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }

        return List.of(result);
    }

    public Map<String, List<Integer>> getActorIdsByParentNode(String projectId) {
        List<Object[]> rows = transactionRepository.findActorIdsGroupedByParentNode(projectId);

        Map<String, Set<Integer>> tempMap = new HashMap<>();
        for (Object[] row : rows) {
            String parentNode = (String) row[0];
            Integer actorId = (Integer) row[1];

            if (parentNode != null && actorId != null) {
                tempMap.computeIfAbsent(parentNode, k -> new HashSet<>()).add(actorId);
            }
        }

        // Convert Set to List for final response
        Map<String, List<Integer>> result = new HashMap<>();
        for (Map.Entry<String, Set<Integer>> entry : tempMap.entrySet()) {
            result.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }

        return result;
    }

    public Map<String, List<Integer>> getFilteredActorIdsByParentNode(String projectId) {
        // Get full map
        Map<String, List<Integer>> fullMap = this.getActorIdsByParentNode(projectId);
        System.out.println(fullMap);

        // Get allowed parent nodes from getNodeActorList
        Set<String> allowedNodes = this.getNodeActorList(projectId)
                .stream()
                .flatMap(map -> map.keySet().stream())
                .collect(Collectors.toSet());

        // Filter to include only allowed parent nodes
        Map<String, List<Integer>> filteredMap = new HashMap<>();
        for (String node : allowedNodes) {
            if (fullMap.containsKey(node)) {
                filteredMap.put(node, fullMap.get(node));
            }
        }

        return filteredMap;
    }

    public List<String> getAllActorIdsAsString(String projectId) {
        List<String> farmerIds = farmerProjectMasterRepository.findFarmerIdsByProjectId(projectId);
        List<Integer> actorIds = transactionRepository.findActorIdsByProjectId(projectId);

        List<String> allIds = new ArrayList<>(farmerIds);
        for (Integer id : actorIds) {
            allIds.add(String.valueOf(id));
        }

        return allIds;
    }

    public Map<String, List<String>> getChildActorsGroupedByParent(String projectId) {
        List<String> allActorIds = this.getAllActorIdsAsString(projectId);

        if (allActorIds.isEmpty()) return Collections.emptyMap();

        List<Object[]> rows = transactionRepository.findActorIdsByParentNodes(projectId, allActorIds);

        // Build a map of parent_node -> List<actor_id>
        Map<String, List<String>> resultMap = new HashMap<>();
        for (String parentId : allActorIds) {
            resultMap.put(parentId, new ArrayList<>());
        }

        for (Object[] row : rows) {
            String parentNode = (String) row[0];
            String actorId = String.valueOf(row[1]);

            if (parentNode != null && actorId != null) {
                resultMap.get(parentNode).add(actorId);
            }
        }

        return resultMap;
    }


    public Map<String, Object> getNodeTransactionSummary(String parentNode, Integer actorId, String projectId) {
        List<Object[]> records = transactionRepository.findTransactionsByParentNodeAndActorId(parentNode, actorId, projectId);

        int noOfTransactions = records.size();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalQuantity = BigDecimal.ZERO;
        Integer totalUnits = 0;
        String unit = "";

        for (Object[] row : records) {
            BigDecimal amount = (BigDecimal) row[0];
            BigDecimal quantity = (BigDecimal) row[1];
            String measuringUnit = (String) row[2];
            Integer no_of_units = (Integer) row[3];

            if (amount != null) totalAmount = totalAmount.add(amount);
            if (quantity != null) totalQuantity = totalQuantity.add(quantity);
            if (no_of_units != null) totalUnits = totalUnits + no_of_units;
            if (unit.isEmpty() && measuringUnit != null) unit = measuringUnit;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("no_of_transactions", noOfTransactions);
        result.put("total_amount", totalAmount);
        result.put("total_quantity", totalQuantity);
        result.put("unit", unit);
        result.put("total_no_of_units", totalUnits);

        return result;
    }

    public List<Map<String, Object>> getNodeTransactionDetails(String parentNode, Integer actorId, String projectId) {
        List<Object[]> records = transactionRepository.findDetailedTransactionsByParentNodeAndActorId(parentNode, actorId, projectId);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : records) {
            Map<String, Object> map = new HashMap<>();
            map.put("blockchain_hash", row[0]);
            map.put("transaction_id", row[1]);
            map.put("process_type", row[2]);
            map.put("transaction_date", row[3]);
            map.put("quantity", row[4]);
            map.put("amount", row[5]);
            map.put("no_of_units", row[6]);
            map.put("measuring_unit", row[7]);
            result.add(map);
        }

        return result;
    }

    public Map<String, Object> getFarmerDetails(String farmerId) {
        Optional<FarmerMaster> farmerOpt = farmerMasterRepository.findById(farmerId);
        if (farmerOpt.isPresent()) {
            FarmerMaster farmer = farmerOpt.get();
            Map<String, Object> result = new HashMap<>();
            result.put("farmer_id", farmer.getFarmer_id());
            result.put("farmer_name", farmer.getFarmer_name());
            result.put("mobile", farmer.getMobile());
            result.put("father_name", farmer.getFather_name());
            result.put("state", farmer.getState());
            result.put("district", farmer.getDistrict());
            result.put("taluka", farmer.getTaluka());
            return result;
        }
        return null;
    }

    public Map<String, Object> getActorDetails(String projectId, Integer actorId) {
        try {
            return actorMasterRepository.findActorDetailsNative(projectId, actorId);
        } catch (Exception e) {
            // You can add a logger here to log the error
            throw new RuntimeException("Failed to fetch actor details: " + e.getMessage(), e);
        }
    }

    public Map<String, Object> createProductViewMaster(Map<String, Object> productViewMaster, String project_id) {
        ProductViewMaster productViewMasterdb = new ProductViewMaster(); // master name rleated to model class 
        productViewMasterdb.setProject_id(project_id);
        productViewMasterdb.setJsonFile(productViewMaster);
        productViewMasterRepository.save(productViewMasterdb);
        return productViewMasterdb.getJsonFile();
    }

    public Map<String, Object> getProductViewJson(String project_id) {
        Optional<ProductViewMaster> productViewMasterdb = productViewMasterRepository.findById(project_id);
        if (productViewMasterdb.isPresent()) {
            return productViewMasterdb.get().getJsonFile();
        }
        return null;
    }

    public List<Map<String, Object>> getActorsAndFarmersforGeographical(String project_id) {
        List<Map<String, Object>> result = new ArrayList<>();

        // Farmers (level = 1)
        List<Map<String, Object>> farmers = transactionRepository.findFarmersByProject(project_id);
        result.addAll(farmers);

        // Actors (level != 1)
        List<Map<String, Object>> actors = transactionRepository.findActorsByProject(project_id);
        result.addAll(actors);

        return result;
    }

    public List<Map<String, Object>> getProjectLevelDetails(String project_id) {
        List<Map<String, Object>> finalResult = new ArrayList<>();

        // 1. Add Level 0 - Farming (hardcoded)
        List<String> farmers = transactionRepository.getFarmerNamesByProjectId(project_id);
        Map<String, Object> level0 = new HashMap<>();
        level0.put("level_number", 0);
        level0.put("level_name", "Farming");
        level0.put("actors", farmers);
        level0.put("asset", "Crop");
        level0.put("process_type", "Farming");
        finalResult.add(level0);

        // 2. Add remaining levels from project_level_master
        List<Map<String, Object>> levelDetails = transactionRepository.getLevelsByProjectId(project_id);
        for (Map<String, Object> level : levelDetails) {
            Integer levelNumber = ((Number) level.get("level_number")).intValue();
            String levelName = (String) level.get("level_name");
            String asset = (String) level.get("asset");
            String processType = (String) level.get("process_type");

            List<String> actorNames = transactionRepository.getActorNamesByProjectAndLevel(project_id, levelNumber);

            Map<String, Object> levelMap = new HashMap<>();
            levelMap.put("level_number", levelNumber);
            levelMap.put("level_name", levelName);
            levelMap.put("actors", actorNames);
            levelMap.put("asset", asset);
            levelMap.put("process_type", processType);

            finalResult.add(levelMap);
        }

        return finalResult;
    }


}
