package com.compass.code.challenge.controller;

import com.compass.code.challenge.service.CompassCodeChallengeService;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("compass-code-challenge")
@AllArgsConstructor
public class CompassCodeChallengeController {

    private final CompassCodeChallengeService compassCodeChallengeService;

    @PostMapping(value = "/process-duplicates")
    public List<List<String>> process(@RequestParam("file") MultipartFile file) throws IOException, CsvValidationException {
        return compassCodeChallengeService.process(file);
    }
}
