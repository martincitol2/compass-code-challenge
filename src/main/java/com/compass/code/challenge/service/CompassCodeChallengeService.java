package com.compass.code.challenge.service;

import com.compass.code.challenge.config.CompassCodeChallengeConfig;
import com.compass.code.challenge.enums.AccuracyEnum;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.compass.code.challenge.util.validation.CompassCodeChallengeValidations.isValidCSV;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@AllArgsConstructor
public class CompassCodeChallengeService {

    private final CompassCodeChallengeConfig compassCodeChallengeConfig;

    public List<List<String>> process(MultipartFile file) throws IOException, CsvValidationException {
        isValidCSV(file);
        List<List<String>> records = getRecords(file);
        List<List<String>> result = getResult();
        for (int i = 0; i < records.size(); i++) {
            for (int j = i+1; j < records.size(); j++) {
                Integer amountOfMatches = getAmountOfMatches(records.get(i), records.get(j));
                if(existMatch(amountOfMatches)){
                    addToResultList(records, i, j, amountOfMatches, result);
                }
            }
        }
        return result;
    }

    void addToResultList(List<List<String>> records, int i, int j, Integer amountOfMatches, List<List<String>> result) {
        List<String> sublist = new ArrayList<>();
        sublist.add(records.get(i).get(0));
        sublist.add(records.get(j).get(0));
        sublist.add(getAccuracy(amountOfMatches).getValue());
        result.add(sublist);
    }

    List<List<String>> getResult() {
        return new ArrayList<>(List.of(List.of("ContactID Source", "ContactID Match", "Accuracy")));
    }

    static boolean existMatch(Integer amountOfMatches) {
        return amountOfMatches > 1;
    }

    //Accuracy depends on the number of matches setting in the configuration file
    public AccuracyEnum getAccuracy(Integer amountOfMatches) {
        if (amountOfMatches >= compassCodeChallengeConfig.getHighAmountOfMatches()) {
            return AccuracyEnum.HIGH;
        } else if (amountOfMatches.equals(compassCodeChallengeConfig.getMediumAmountOfMatches())) {
            return AccuracyEnum.MEDIUM;
        } else {
            return AccuracyEnum.LOW;
        }
    }

    //Count number of matches avoids the first element
    Integer getAmountOfMatches(List<String> contact, List<String> otherContact) {
        Integer amountOfMatches = 0;
        for (int i = 1; i <= contact.size()-1; i++) {
            if (isValidToCompare(i, contact, otherContact) && compareList(contact, otherContact, i)) {
                amountOfMatches++;
            }
        }
        return amountOfMatches;
    }

    private static boolean compareList(List<String> contact, List<String> otherContact, int i) {
        return contact.get(i).equalsIgnoreCase(otherContact.get(i));
    }

    private static boolean isValidToCompare(int i, List<String> contact, List<String> otherContact) {
        return isNotEmpty(contact.get(i)) && isNotEmpty(otherContact.get(i));
    }

    static List<List<String>> getRecords(MultipartFile file) throws IOException, CsvValidationException {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }
}
