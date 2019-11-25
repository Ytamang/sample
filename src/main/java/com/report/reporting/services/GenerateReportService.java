package com.report.reporting.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GenerateReportService {

    ExcelReportGenerator excelReportGenerator;

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GenerateReportService(ExcelReportGenerator excelReportGenerator) {
        this.excelReportGenerator = excelReportGenerator;
    }

    public ResponseEntity<InputStreamResource> generateReport(String todaysDate) throws IOException {
        ByteArrayInputStream in = excelReportGenerator.generateExcelDocumnet();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=EDGE Report for " + todaysDate + ".xls");
        headers.add("Content-Type", "application/vnd.ms-excel");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    // Cache evict
    @Scheduled(cron = "0 0 * * * *")
    @CacheEvict("Generated Report")
    public void removeCache() {
        LOG.info("Cache has been cleared for the day" + new Date());
    }

    @Scheduled(cron = "0 00 5 * * *")
    public ResponseEntity<InputStreamResource> generateReport() throws IOException {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = simpleDateFormat.format(now);
        byte[] in = excelReportGenerator.generateExcelDocumnetEnableCaching();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=EDGE Report for " + todaysDate + ".xls");
        headers.add("Content-Type", "application/vnd.ms-excel");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(new ByteArrayInputStream(in)));
    }
}


