package com.report.reporting.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;

@Document(collection = "CameraStatus")
public class CameraStatus implements Serializable {
	
	private String id;
	private Long sensorId;
    private Timestamp startTime;
   
	private Long temperature;
    private Long storeNumber;
    private Long leftAisleNumber;
    private Long rightAisleNumber;
    private String Orientation;
    private Long bayNumber;
    private String messageType;
    private String source;
    private Long messageVersion;
    private String facilityId;
    private String wifiMacAddress;
    private String divisionNumber;
    
    public CameraStatus(){
    }
	public String getId() {
		return id;
	}
	
	public CameraStatus(String id, Long temperature, Long storeNumber,
			Long leftAisleNumber, Long rightAisleNumber, String orientation, Long bayNumber, String messageType,
			String source, Long messageVersion, String facilityId, String wifiMacAddress, String divisionNumber) {
		super();
		this.id = id;
		this.temperature = temperature;
		this.storeNumber = storeNumber;
		this.leftAisleNumber = leftAisleNumber;
		this.rightAisleNumber = rightAisleNumber;
		Orientation = orientation;
		this.bayNumber = bayNumber;
		this.messageType = messageType;
		this.source = source;
		this.messageVersion = messageVersion;
		this.facilityId = facilityId;
		this.wifiMacAddress = wifiMacAddress;
		this.divisionNumber = divisionNumber;
	}


	public CameraStatus(String id, Long s,Timestamp startTime, Long storeNumber,
			 String facilityId, String divisionNumber) {
		super();
		this.id = id;
		this.sensorId=s;
		this.startTime = startTime;
		this.storeNumber = storeNumber;
		this.facilityId = facilityId;
		this.divisionNumber = divisionNumber;
	}

	public CameraStatus(String uuid,Timestamp startTime, int i, int j, Object leftAisleNumber2, int k, String orientation2, int l,
			String messageType2, String source2, String string, String facilityId2, String wifiMacAddress2,
			String divisionNumber2) {
		this.id = id;
		this.startTime=startTime;
		this.temperature = temperature;
		this.storeNumber = storeNumber;
		this.leftAisleNumber = leftAisleNumber;
		this.rightAisleNumber = rightAisleNumber;
		this.Orientation = orientation2;
		this.bayNumber = bayNumber;
		this.messageType = messageType;
		this.source = source;
		this.messageVersion = messageVersion;
		this.facilityId = facilityId;
		this.wifiMacAddress = wifiMacAddress;
		this.divisionNumber = divisionNumber;
	}

	 public Long getSensorId() {
			return sensorId;
		}
		public void setSensorId(Long sensorId) {
			this.sensorId = sensorId;
		}
	public void setId(String id) {
		this.id = id;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Long getTemperature() {
		return temperature;
	}
	public void setTemperature(Long temperature) {
		this.temperature = temperature;
	}
	public Long getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(Long storeNumber) {
		this.storeNumber = storeNumber;
	}
	public Long getLeftAisleNumber() {
		return leftAisleNumber;
	}
	public void setLeftAisleNumber(Long leftAisleNumber) {
		this.leftAisleNumber = leftAisleNumber;
	}
	public Long getRightAisleNumber() {
		return rightAisleNumber;
	}
	public void setRightAisleNumber(Long rightAisleNumber) {
		this.rightAisleNumber = rightAisleNumber;
	}
	public String getOrientation() {
		return Orientation;
	}
	public void setOrientation(String orientation) {
		Orientation = orientation;
	}
	public Long getBayNumber() {
		return bayNumber;
	}
	public void setBayNumber(Long bayNumber) {
		this.bayNumber = bayNumber;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Long getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(Long messageVersion) {
		this.messageVersion = messageVersion;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getWifiMacAddress() {
		return wifiMacAddress;
	}
	public void setWifiMacAddress(String wifiMacAddress) {
		this.wifiMacAddress = wifiMacAddress;
	}
	public String getDivisionNumber() {
		return divisionNumber;
	}
	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}

}
