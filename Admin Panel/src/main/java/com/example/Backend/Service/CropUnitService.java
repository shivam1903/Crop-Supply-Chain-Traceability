package com.example.Backend.Service;

import com.example.Backend.Repository.CropUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropUnitService {

    @Autowired
    private CropUnitRepository cropUnitRepository;

    public List<String> getUniqueCrops(){
        return cropUnitRepository.findUniqueCrops();
    }

    public List<String> getUnitsforCrop(String crop){
        return cropUnitRepository.findUnitsforCrop(crop);
    }

}
