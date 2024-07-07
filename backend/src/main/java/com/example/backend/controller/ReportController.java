package com.example.backend.controller;

import com.example.backend.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/first")
    public ResponseEntity<?> generateReportSql() {
        return ResponseEntity.ok(reportService.createReport());
    }

    @GetMapping("/nedim/sql")
    public ResponseEntity<?> generateReportNedimSql() {
        return ResponseEntity.ok(reportService.createReportNedimSql());
    }

}
