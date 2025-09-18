package com.example.Backend.Service;

import com.example.Backend.Repository.ActorRepository;
import com.example.Backend.Repository.TransactionAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionAuditService {

    @Autowired
    private TransactionAuditRepository transactionAuditRepository;

    @Autowired
    private ActorRepository actorRepository;

    public List<Map<String, Object>> getTransactionforProject(String project_id) {
        List<Object[]> results=  transactionAuditRepository.findTransactionforProject(project_id);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("transaction_id",  result[0]);
            map.put("entry_date", result[1]);
            map.put("actor_id", result[2]);
            map.put("parent_node", result[3]);
            map.put("quantity", result[4]);
            map.put("total_amount", result[5]);
            map.put("remark", result[6]);
            map.put("level", result[7]);
            map.put("measuring_unit", result[8]);
            map.put("no_of_units", result[9]);
            map.put("mandi_name", actorRepository.getMandiName( (Integer) result[2], project_id));
            map.put("firm_name", actorRepository.getFirmName((Integer) result[2], project_id));
            return map;
        }).collect(Collectors.toList());

    }

}