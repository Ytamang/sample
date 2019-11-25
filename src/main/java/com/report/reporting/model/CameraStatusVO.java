package com.report.reporting.model;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class CameraStatusVO {
	private Long sensorId;
	private String facilityId;
	private String divisionId;
	private Timestamp insertTs;
	private String camstatus;
	public Long getSensorId() {
		return sensorId;
	}
	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}
	public Timestamp getInsertTs() {
		return insertTs;
	}
	public void setInsertTs(Timestamp insertTs) {
		this.insertTs = insertTs;
	}
	public String getCamstatus() {
		return camstatus;
	}
	public void setCamstatus(String camstatus) {
		this.camstatus = camstatus;
	}
}
