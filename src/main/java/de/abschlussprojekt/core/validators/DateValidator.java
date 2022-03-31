package de.abschlussprojekt.core.validators;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Service
public class DateValidator {

    public void validate(LocalDateTime localDateTime) {
        if (!localDateTime.isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Date " + localDateTime + " is not today or in the future");
        }
    }

    public LocalDateTime mapToLocalDateTime(String localDateTime) {
        try {
            return LocalDateTime.parse(localDateTime);
        } catch (DateTimeParseException e) {
            return LocalDateTime.parse(localDateTime + "T00:00");
        }
    }

    public boolean hasTime(String localDateTime) {
        try {
            LocalDateTime.parse(localDateTime);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }


}
