package com.report.reporting.services;

import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table.Cell;
import com.report.reporting.model.CameraStatus;
import com.report.reporting.model.CameraStatusVO;
import com.report.reporting.repositories.CamerasStausRepository;

@Service
public class ExcelReportGenerator {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    ReportEmailUtility reportEmailUtility;

    @Autowired
    CamerasStausRepository cameraStatusRepository;

    public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;

    public ByteArrayInputStream generateExcelDocumnet() throws IOException {
        LOG.info("Report is being created - please wait...");
        String[] COLUMNs = {"ShelfID", "FacilityId", "DivisionId", "Shelf Running Status", "Last Ping"};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            createExcel(COLUMNs, workbook, out);

//            // Send email
//            File excel = new File("VA-EventMessagesReport" + ".xls");
//            FileOutputStream fos = new FileOutputStream(excel);
//            workbook.write(fos);
//            fos.close();
//
//            String yesterdayDate = "2019-07-07 00:00:00";
//            reportEmailUtility.sendReportEmail("Threshold", excel, yesterdayDate);
            LOG.info("The current Report is generated");
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @Cacheable("Generated Report")
    public byte[] generateExcelDocumnetEnableCaching() throws IOException {
        LOG.info("Scheduled Report is being created ....");
        String[] COLUMNs = {"ShelfID", "FacilityId", "DivisionId", "Shelf Running Status", "Last Ping"};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            createExcel(COLUMNs, workbook, out);
            byte[] response = out.toByteArray();
            LOG.info("Scheduled  Report has been generated");
            return response;
        }
    }

    private void createExcel(String[] COLUMNs, Workbook workbook, ByteArrayOutputStream out) throws IOException {
        List<CameraStatus> cameraStatusList = getAllCameraStatus();

        Sheet sheet = workbook.createSheet("Edge Shelves Status");
//        Font headerFont = workbook.createFont();
        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont.setBold(true);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

        // Row for Header
        Row headerRow = sheet.createRow(0);

        // Header
        for (int col = 0; col < COLUMNs.length; col++) {
            org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(col);
            cell.setCellValue(COLUMNs[col]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowIdx = 1;
        Date now = new Date();
        for (CameraStatus cameraStatus : cameraStatusList) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(cameraStatus.getSensorId());
            row.createCell(1).setCellValue(cameraStatus.getFacilityId());
            row.createCell(2).setCellValue(cameraStatus.getDivisionNumber());
            if (Math.abs(now.getTime() - cameraStatus.getStartTime().getTime()) <= MILLIS_PER_DAY) {
                org.apache.poi.ss.usermodel.Cell cell = row.createCell(3);
                CellStyle goodStyle = workbook.createCellStyle();
                goodStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                goodStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellValue("GOOD");
                cell.setCellStyle(goodStyle);
            } else {
                org.apache.poi.ss.usermodel.Cell cell = row.createCell(3);
                CellStyle goodStyle = workbook.createCellStyle();
                goodStyle.setFillForegroundColor(IndexedColors.CORAL.getIndex());
                goodStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellValue("BAD");
                cell.setCellStyle(goodStyle);
            }
            row.createCell(4).setCellValue(cameraStatus.getStartTime() != null ? cameraStatus.getStartTime().toString() : "0");
        }
        workbook.write(out);
    }

    public List<CameraStatus> getAllCameraStatus() {
//        List<CameraStatus> cameraStatusList = cameraStatusRepository.findAll().stream().
//                filter(cameraStatus -> cameraStatus != null && cameraStatus.getInsertTs() != null).collect(Collectors.toList());

    	List<CameraStatus> cameraStatusList = new ArrayList<CameraStatus>();
    	
    	  Iterable<CameraStatus>  result1 =  cameraStatusRepository.findAll();
    	  for(CameraStatus c :result1){
    		  if(c.getStartTime()!= null){
    			  cameraStatusList.add(c);
    		  }
    	  }
    	  
      	
    	Map<Long, CameraStatus> rescentCameraStatusMap = cameraStatusList.stream()
                .collect(Collectors.toMap(CameraStatus::getSensorId, Function.identity(),
                        BinaryOperator.maxBy(Comparator.nullsFirst((Comparator.comparing(CameraStatus::getStartTime))))));
        
        
        List<CameraStatus> recentCameraStatusList = new ArrayList<CameraStatus>(rescentCameraStatusMap.values());
        recentCameraStatusList.sort(Comparator.comparingLong(CameraStatus::getSensorId));

        return recentCameraStatusList;
    }

    @Scheduled(cron = "0 */5 * * * *")
    @Cacheable("List of CameraStatus")
    public List<CameraStatusVO> getCameraStatus() {
        LOG.info("running scheduled" + new Date());
        Date now = new Date();
        List<CameraStatus> cameraStatusList = getAllCameraStatus();
        List<CameraStatusVO> camerlist = new ArrayList<CameraStatusVO>();
        for (CameraStatus cameraStatus : cameraStatusList) {
        	CameraStatusVO cameraStatusModel = new CameraStatusVO();
            cameraStatusModel.setSensorId(cameraStatus.getSensorId());
            cameraStatusModel.setDivisionId(cameraStatus.getDivisionNumber());
            cameraStatusModel.setFacilityId(cameraStatus.getFacilityId());
            cameraStatusModel.setInsertTs(cameraStatus.getStartTime());
            cameraStatusModel.setCamstatus(Math.abs(now.getTime() - cameraStatus.getStartTime().getTime()) <= MILLIS_PER_DAY ? "GOOD" : "BAD");
            camerlist.add(cameraStatusModel);
        }
        LOG.info("scheduled job completed at  " + new Date());
        return camerlist;
    }

    @Scheduled(cron = "0 */5 * * * *")
    @CacheEvict("List of CameraStatus")
    public void deleteListOfCameracahce() {
        LOG.info("The camera cache is cleared  : " + new Date());
    }

}
