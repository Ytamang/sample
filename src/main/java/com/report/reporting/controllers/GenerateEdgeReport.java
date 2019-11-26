package com.report.reporting.controllers;

import io.swagger.annotations.ApiOperation;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.report.reporting.services.ExcelReportGenerator;
import com.report.reporting.services.GenerateReportService;

@RestController
@RequestMapping("/api/edge/reports")
public class GenerateEdgeReport {

    @Autowired
    GenerateReportService generateReportService;

    @Autowired
    ExcelReportGenerator excelReportGenerator;

    @ApiOperation("Generate a current report on the EDGE shelves status")
    @GetMapping(value = "/generateReport")
    public ResponseEntity<InputStreamResource> generateCurrentReport() throws Exception {
        return generateCurrentReport(null);
    }

    @ApiOperation("Get the EDGE shelves status report generated this morning at 5:00AM")
    @GetMapping(value = "/getReport")
    public ResponseEntity<InputStreamResource> getTodaysReport() throws Exception {
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        return generateCurrentReport(simpleDateFormat.parse(simpleDateFormat.format(today)));
    }

    public ResponseEntity<InputStreamResource> generateCurrentReport(Date specifiedDate) throws Exception {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        if (specifiedDate == null) {
            return generateReportService.generateReport(simpleDateFormat.format(now));
        } else {
            return generateReportService.generateReport();
        }
    }
<<<<<<< Updated upstream


//abcd
=======
    //edited to master.
>>>>>>> Stashed changes
}
