package de.abschlussprojekt.core.validators;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@ApplicationScoped
public class DateValidator {

    public void validate(LocalDateTime localDateTime) {
        if (!localDateTime.isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Date " + localDateTime + " is not today or in the future");
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
