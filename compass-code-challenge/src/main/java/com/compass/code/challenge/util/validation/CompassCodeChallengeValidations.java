package com.compass.code.challenge.util.validation;


import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

import static com.compass.code.challenge.util.constants.CompassCodeChallengeConstants.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompassCodeChallengeValidations {

    public static void isValidCSV(MultipartFile file) {
        if (isNull(file) || isEmpty(file.getOriginalFilename()) ||
                Arrays.stream(file.getOriginalFilename().split(ESCAPED_DOT)).reduce((first, second) -> second).stream()
                        .noneMatch(s -> s.equalsIgnoreCase(CSV))) {
            throw new ValidationException(INVALID_CSV_FORMAT);
        }
    }

}
