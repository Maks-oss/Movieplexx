package com.example.backend.generator;

import com.example.backend.generator.DataGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class DataGeneratorController {
    @Autowired
    private DataGeneratorService generatorService;
    @PostMapping("/generate")
    public ResponseEntity<?> generateData(@RequestParam(required = false) Boolean newData, UriComponentsBuilder uriComponentsBuilder) {
        generatorService.generateData(newData != null && newData);
//        return ResponseEntity.ok(generatorService.retrieveGeneratedData());
        var uri = uriComponentsBuilder.path("/generate").query("type=YOUR_TYPE").build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping("/generate")
    public ResponseEntity<?> getGeneratedData(@RequestParam(required = false,defaultValue = "emp") String type) {
        return ResponseEntity.ok(generatorService.retrieveGeneratedData(type));
    }

}
