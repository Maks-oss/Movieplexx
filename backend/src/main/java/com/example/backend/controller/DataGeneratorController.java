package com.example.backend.controller;

import com.example.backend.DataGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DataGeneratorController {
    @Autowired
    private DataGeneratorService generatorService;
    @RequestMapping("/generate")
    public ResponseEntity<?> generateData(@RequestParam(required = false) Boolean newData) {
        generatorService.generateData(newData != null && newData);
        return ResponseEntity.ok(generatorService.retrieveGeneratedData());
    }

}
