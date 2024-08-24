package com.compass.code.challenge.service;

import com.compass.code.challenge.config.CompassCodeChallengeConfig;
import com.compass.code.challenge.enums.AccuracyEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompassCodeChallengeServiceTest {

    @InjectMocks
    private CompassCodeChallengeService compassCodeChallengeService;

    @Mock
    private CompassCodeChallengeConfig compassCodeChallengeConfig;

    @Test
    void testGetAmountOfMatches_AllMatches() {
        List<String> contact = Arrays.asList("John", "Doe", "john@example.com");
        List<String> otherContact = Arrays.asList("John", "Doe", "john@example.com");

        Integer result = compassCodeChallengeService.getAmountOfMatches(contact, otherContact);

        assertEquals(2, result); // Should match "Doe" and "john@example.com"
    }

    @Test
    void testGetAmountOfMatches_SomeMatches() {
        List<String> contact = Arrays.asList("John", "Doe", "john@example.com");
        List<String> otherContact = Arrays.asList("John", "Smith", "john@example.com");

        Integer result = compassCodeChallengeService.getAmountOfMatches(contact, otherContact);

        assertEquals(1, result); // Should match "john@example.com"
    }

    @Test
    void testGetAmountOfMatches_NoMatches() {
        List<String> contact = Arrays.asList("John", "Doe", "john@example.com");
        List<String> otherContact = Arrays.asList("John", "Smith", "jane@example.com");

        Integer result = compassCodeChallengeService.getAmountOfMatches(contact, otherContact);

        assertEquals(0, result);
    }

    @Test
    void testGetAmountOfMatches_EmptyLists() {
        List<String> contact = Arrays.asList("", "", "");
        List<String> otherContact = Arrays.asList("", "", "");

        Integer result = compassCodeChallengeService.getAmountOfMatches(contact, otherContact);

        assertEquals(0, result);
    }

    @Test
    void testGetAccuracy_HighAccuracy() {
        int highAmountOfMatches = 10;
        when(compassCodeChallengeConfig.getHighAmountOfMatches()).thenReturn(highAmountOfMatches);

        AccuracyEnum result = compassCodeChallengeService.getAccuracy(12);

        assertEquals(AccuracyEnum.HIGH, result);
    }

    @Test
    void testGetAccuracy_MediumAccuracy() {
        int mediumAmountOfMatches = 5;
        int highAmountOfMatches = 10;
        when(compassCodeChallengeConfig.getMediumAmountOfMatches()).thenReturn(mediumAmountOfMatches);
        when(compassCodeChallengeConfig.getHighAmountOfMatches()).thenReturn(highAmountOfMatches);


        AccuracyEnum result = compassCodeChallengeService.getAccuracy(5);

        assertEquals(AccuracyEnum.MEDIUM, result);
    }

    @Test
    void testGetAccuracy_LowAccuracy() {
        int highAmountOfMatches = 10;
        int mediumAmountOfMatches = 5;
        when(compassCodeChallengeConfig.getHighAmountOfMatches()).thenReturn(highAmountOfMatches);
        when(compassCodeChallengeConfig.getMediumAmountOfMatches()).thenReturn(mediumAmountOfMatches);

        AccuracyEnum result = compassCodeChallengeService.getAccuracy(3);

        assertEquals(AccuracyEnum.LOW, result);
    }
}
