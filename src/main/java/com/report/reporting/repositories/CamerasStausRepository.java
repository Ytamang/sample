package com.report.reporting.repositories;

import java.util.List;

import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.stereotype.Repository;

import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import com.report.reporting.model.CameraStatus;
@Repository
public interface CamerasStausRepository extends DocumentDbRepository<CameraStatus, String> {
	
	List<CameraStatus> findByStoreNumber(Long storeNumber);
	
}
