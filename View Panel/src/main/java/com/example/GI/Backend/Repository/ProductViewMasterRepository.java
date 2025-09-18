package com.example.GI.Backend.Repository;


import com.example.GI.Backend.model.ProductViewMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProductViewMasterRepository extends JpaRepository<ProductViewMaster, String> {
    // 
}

