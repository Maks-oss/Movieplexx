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

    @GetMapping("/first")
    public ResponseEntity<?> generateReport() {
        return ResponseEntity.ok(reportService.createReport());
    }
}
