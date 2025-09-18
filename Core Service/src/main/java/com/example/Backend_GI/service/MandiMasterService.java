package com.example.Backend_GI.service;

import com.example.Backend_GI.model.MandiMaster;
import com.example.Backend_GI.repository.MandiMasterRepository;
import com.example.Backend_GI.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MandiMasterService {

    @Autowired
    private MandiMasterRepository mandiMasterRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Map<String, Object>> getMandibyDistrict(String district) {
        List<Object[]> results = mandiMasterRepository.findMandibyDistrict(district);
        return results.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("mandi_name", result[0]);
            map.put("id", result[1]);
            return map;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void resetSequence() {
        entityManager.createNativeQuery(
                "DO $$ BEGIN PERFORM setval('mandi_master_id_seq_a', COALESCE((SELECT MAX(id) FROM mandi_master), 1)); END $$;"
        ).executeUpdate();
    }

    @Transactional
    public Integer saveMandi(MandiMaster mandiMaster) {

        this.resetSequence();
        MandiMaster savedMandi = mandiMasterRepository.save(mandiMaster);

        return savedMandi.getId();
    }
}
