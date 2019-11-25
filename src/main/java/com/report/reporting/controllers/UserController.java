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

    @CrossOrigin
    @GetMapping("/getDivisions")
    public List<String> getAllDivisions() {
    	System.out.println("**********     getDivisions      *************");
        return apiService.getAllDivisions();
    }

    @CrossOrigin
    @GetMapping("/getFacilities}")
    public List<String> getAllFacilities() {
    	System.out.println("**********     getFacilities Controller      *************");
    	return apiService.getAllFacilities();
    }

    @CrossOrigin
	@GetMapping("/hello")
    public String hello(){
    	return "hello";
	}
    
}
