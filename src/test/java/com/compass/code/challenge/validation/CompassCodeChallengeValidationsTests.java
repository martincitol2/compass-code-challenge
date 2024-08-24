package com.compass.code.challenge.validation;


import com.compass.code.challenge.util.validation.CompassCodeChallengeValidations;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CompassCodeChallengeValidationsTests {
    private static final String INVALID_CSV_FORMAT = "Invalid CSV format";

    @Test
    void testIsValidCSV_ValidCSVFile() {
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "Name,Email\nJohn,john@example.com".getBytes());

        assertDoesNotThrow(() -> CompassCodeChallengeValidations.isValidCSV(file));
    }

    @Test
    void testIsValidCSV_NullFile() {
        MultipartFile file = null;

        assertThrows(ValidationException.class, () -> CompassCodeChallengeValidations.isValidCSV(file), INVALID_CSV_FORMAT);
    }

    @Test
    void testIsValidCSV_EmptyFilename() {
        MultipartFile file = new MockMultipartFile("file", "", "text/csv", "Name,Email\nJohn,john@example.com".getBytes());

        assertThrows(ValidationException.class, () -> CompassCodeChallengeValidations.isValidCSV(file), INVALID_CSV_FORMAT);
    }

    @Test
    void testIsValidCSV_InvalidExtension() {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Name,Email\nJohn,john@example.com".getBytes());

        assertThrows(ValidationException.class, () -> CompassCodeChallengeValidations.isValidCSV(file), INVALID_CSV_FORMAT);
    }

    @Test
    void testIsValidCSV_ValidButCaseInsensitiveExtension() {
        MultipartFile file = new MockMultipartFile("file", "test.CSV", "text/csv", "Name,Email\nJohn,john@example.com".getBytes());

        assertDoesNotThrow(() -> CompassCodeChallengeValidations.isValidCSV(file));
    }

}
