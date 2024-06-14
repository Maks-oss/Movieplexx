package com.example.backend.report;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/first/sql")
    public ResponseEntity<?> generateReportSql() {
        return ResponseEntity.ok(reportService.createReport());
    }

    @GetMapping("/first/nosql")
    public ResponseEntity<?> generateReportNoSql() {
        return ResponseEntity.ok(reportService.createReportNoSql());
    }

    @GetMapping("/nedim/sql")
    public ResponseEntity<?> generateReportNedimSql() {
        return ResponseEntity.ok(reportService.createReportNedimSql());
    }

    @GetMapping("/nedim/nosql")
    public ResponseEntity<?> generateReportNedimNoSql() {
        return ResponseEntity.ok(reportService.createReportNedimNoSql());
    }

}
