package com.example.backend.controller;

import com.example.backend.service.DataGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class DataGeneratorController {
    @Autowired
    private DataGeneratorService generatorService;


    @PostMapping("/generate")
    public ResponseEntity<?> generateData(UriComponentsBuilder uriComponentsBuilder) {
        generatorService.generateData();
        var uri = uriComponentsBuilder.path("/generate").query("type=YOUR_TYPE").build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping("/generate")
    public ResponseEntity<?> getGeneratedData(@RequestParam(defaultValue = "emp") String type) {
        return ResponseEntity.ok(generatorService.retrieveGeneratedData(type));
    }

}
