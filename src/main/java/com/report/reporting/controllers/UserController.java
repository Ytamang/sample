package com.report.reporting.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.report.reporting.model.CameraStatus;
import com.report.reporting.model.CameraStatusRequest;
import com.report.reporting.model.CameraStatusVO;
import com.report.reporting.services.ApiService;

@RestController
public class UserController {


//    private final CamerasStausRepository crRepository;
	private final ApiService apiService;
    public UserController(ApiService apiService) {
//    	this.crRepository=crRepository;
    	this.apiService=apiService;
    	try{
    		 String uuid = UUID.randomUUID().toString();
    	        CameraStatus testUser = new CameraStatus(uuid,1L,new Timestamp(System.currentTimeMillis()), 44L,"1004","2004");
    	        CameraStatus testUser2 = new CameraStatus(uuid,1L,new Timestamp(System.currentTimeMillis()), 44L,"1003","2003");
    	        CameraStatus testUser3= new CameraStatus(uuid,1L,new Timestamp(System.currentTimeMillis()), 44L,"1005","2005");
    	        CameraStatus testUser4= new CameraStatus(uuid,1L,new Timestamp(System.currentTimeMillis()), 44L,"1006","2006");
    	        CameraStatus testUser5= new CameraStatus(uuid,1L,new Timestamp(System.currentTimeMillis()), 44L,"1007","2007");
    	        CameraStatus testUser6= new CameraStatus(uuid,1L,new Timestamp(System.currentTimeMillis()), 44L,"1008","2008");
    	        CameraStatus testUser7= new CameraStatus(uuid,2L,new Timestamp(System.currentTimeMillis()), 44L,"1009","2009");
    	        CameraStatus testUser8= new CameraStatus(uuid,2L,new Timestamp(System.currentTimeMillis()), 44L,"1010","2010");
    	        CameraStatus testUser9= new CameraStatus(uuid,3L,new Timestamp(System.currentTimeMillis()), 44L,"1001","2001");
    	        CameraStatus testUser10= new CameraStatus(uuid,3L,new Timestamp(System.currentTimeMillis()), 44L,"1002","2002");
    	        CameraStatus testUser11= new CameraStatus(uuid,4L,new Timestamp(System.currentTimeMillis()), 44L,"1011","2011");
    	        CameraStatus testUser12 = new CameraStatus(uuid,4L,new Timestamp(System.currentTimeMillis()), 44L,"1012","2012");
    	        // Save the User class to the Azure database.
//    	        userRepository.save(testUser);
    	        testUser= apiService.saveCameraStatus(testUser);
    	/*        apiService.saveCameraStatus(testUser);
    	        apiService.saveCameraStatus(testUser2);
    	        apiService.saveCameraStatus(testUser3);
    	        apiService.saveCameraStatus(testUser4);
    	        apiService.saveCameraStatus(testUser5);
    	        apiService.saveCameraStatus(testUser6);
    	        apiService.saveCameraStatus(testUser7);
    	        apiService.saveCameraStatus(testUser8);
    	        apiService.saveCameraStatus(testUser9);
    	        apiService.saveCameraStatus(testUser10);
    	        apiService.saveCameraStatus(testUser11);
    	        apiService.saveCameraStatus(testUser12);*/
System.out.println("################################## #                 ####"+testUser.getId());
    	        // Retrieve the database record for the User class you just saved by ID.
CameraStatus result = apiService.findById(testUser.getId());
// List<CameraStatusPOJO> dd = crRepository.findByStoreNumber(1L);
    	        // Display the results of the database record retrieval.
    	        System.out.println(" ==========\nSaved user is: " + result + "\n");
    	}catch (Exception e) {
    		e.printStackTrace();
    	}

        
    }

    @CrossOrigin
    @GetMapping("/all")
    public List<CameraStatusVO> getAllCameraStatus() {
//    	System.out.println("2222222222222222222222222222222");
        List<CameraStatus> result = new ArrayList<CameraStatus>();
        Iterable<CameraStatus>  result1 =  apiService.findAllCameraStatus();
    	result1.forEach(result::add); 
//    	System.out.println("--------------------------> "+result);
    	
    	List<CameraStatusVO> vos= new ArrayList<CameraStatusVO>();
    	for(CameraStatus cv: result){
//    		System.out.println("---------------results cv  -----------> "+cv.getId());
    		CameraStatusVO vo = new CameraStatusVO();
    		vo.setDivisionId(cv.getDivisionNumber());
    		vo.setSensorId(cv.getStoreNumber());
    		Timestamp ts=new Timestamp(System.currentTimeMillis());
    		System.out.println((ts.getTime() - cv.getStartTime().getTime() <= 60*60*24*1000 )+"==="+(ts.getTime() - cv.getStartTime().getTime())+"   =============   "+ts.getTime()+" ========  "+cv.getStartTime().getTime());
    		vo.setCamstatus( ts.getTime() - cv.getStartTime().getTime() <= 60*60*24 ? "GOOD" : "BAD");
    		vo.setFacilityId(cv.getFacilityId());
    		vo.setInsertTs(cv.getStartTime());
    		vos.add(vo);
    	}
    	System.out.println("---------------results  -----------> "+vos);
        return vos;
    }
   /* @CrossOrigin
    @GetMapping("/store/{storeId}")
    public CameraStatusVO getDetailsByStoreId(@PathVariable("storeId") Long storeId) {
        CameraStatus cs =crRepository.findByStoreNumber(storeId).get(0);
        
        
        CameraStatusVO vo = new CameraStatusVO();
		vo.setDivisionId(cs.getDivisionNumber());
		vo.setSensorId(cs.getBayNumber());
		
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		vo.setCamstatus( ts.getTime() - cs.getStartTime().getTime() <= 60*60*24*1000? "GOOD" : "BAD");
		vo.setFacilityId(cs.getFacilityId());
		vo.setInsertTs(cs.getStartTime());
		return vo;
    }
    */
    @CrossOrigin
    @PostMapping("/listStore")
    public List<CameraStatusVO> returnStoreCameraStatus(@RequestBody CameraStatusRequest request) {
        if (request == null || request.getFacilityId()==null || request.getDivisionId() == null){
        	System.out.println(request.getDivisionId()+" listStore               all null"+request.getFacilityId());
            return getAllCameraStatus();
        }
        if ((request.getDivisionId().isEmpty() || request.getDivisionId().equalsIgnoreCase("ALL"))
                &&
                (request.getFacilityId().isEmpty() || request.getFacilityId().equalsIgnoreCase("ALL"))) {
        	System.out.println(" listStore               get ALL");
            return getAllCameraStatus();
        } else {
        	System.out.println(request.getDivisionId()+" listStore        else       all null"+request.getFacilityId());
            List<CameraStatusVO> neededStoreStatus = getAllCameraStatus().stream().
                    filter(cameraStatus -> cameraStatus.getDivisionId().equals(request.getDivisionId())
                            && cameraStatus.getFacilityId().equals(request.getFacilityId())).collect(Collectors.toList());
            return neededStoreStatus;
        }

    }

//    @CrossOrigin
//    @GetMapping("/getCameraStatus")
//    public List<CameraStatusPOJO> getCameraStatus() {
//        return apiService.getCameraStatus();
//    }
    
    @CrossOrigin
    @GetMapping("/getDivisions")
    public List<String> getAllDivisions() {
    	System.out.println("**********     getDivisions      *************");
        return apiService.getAllDivisions();
    }

    @CrossOrigin
    @GetMapping("/getFacilities")
    public List<String> getAllFacilities() {
    	System.out.println("**********     getFacilities Controller      *************");
    	return apiService.getAllFacilities();
    }
    
    
    
}
