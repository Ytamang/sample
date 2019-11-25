package com.report.reporting.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.report.reporting.model.CameraStatus;
import com.report.reporting.model.CameraStatusVO;
import com.report.reporting.repositories.CamerasStausRepository;

@Service
public class ApiService {

    @Autowired
    CamerasStausRepository cameraStatusRepository;
    
//	public List<String> getAllDivisions() {
//        return cameraStatusRepository.getAllDivisions();
//    }
//
//    public List<String> getAllFacilities() {
//        return cameraStatusRepository.getAllFacilities();
//    }
    
    public List<CameraStatus> getCameraStatus() {
    	List<CameraStatus> cp= new ArrayList<>();
//    	Iterable<CameraStatusPOJO>  itr =camerasStausPojoRepository.findAll();
//    	itr.forEach(cp::add); 
    	
        return cp;
    }

	public CameraStatus saveCameraStatus(CameraStatus testUser) {
		testUser=  cameraStatusRepository.save(testUser);
		return testUser;
	}

	public CameraStatus findById(String id) {
		CameraStatus cs =cameraStatusRepository.findById(id).get();
		return cs;
	}
	
	   public Iterable<CameraStatus> findAllCameraStatus() {
	        return cameraStatusRepository.findAll();
	    }

		// TODO Auto-generated method stub
	   public List<String> getAllDivisions() {
		   
		   List<CameraStatus> result = new ArrayList<CameraStatus>();
	        Iterable<CameraStatus>  result1 =  cameraStatusRepository.findAll();
	    	result1.forEach(result::add); 
	    	System.out.println("------------     getDivisionNumber   -------------> "+result);
	    	List<String> vos= new ArrayList<String>();
	    	for(CameraStatus cv: result){
//	    		System.out.println("---------------results cv  -----------> "+cv.getId());
	    		vos.add(cv.getDivisionNumber());
	    	}
	    	System.out.println("---------------results API service getDivisionNumber  -----------> "+vos);
	        return vos;
	}

	public List<String> getAllFacilities() {
		 List<CameraStatus> result = new ArrayList<CameraStatus>();
	        Iterable<CameraStatus>  result1 =  cameraStatusRepository.findAll();
	    	result1.forEach(result::add); 
	    	System.out.println("--------getAllFacilities------------------> "+result);
	    	
	    	List<String> vos= new ArrayList<String>();
	    	for(CameraStatus cv: result){
//	    		System.out.println("---------------results cv  -----------> "+cv.getId());
	    		vos.add(cv.getFacilityId());
	    	}
	    	System.out.println("---------------results API service  -----------> "+vos);
	        return vos;
	}

}