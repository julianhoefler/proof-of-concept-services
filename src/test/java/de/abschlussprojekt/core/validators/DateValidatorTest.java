package de.abschlussprojekt.core.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class DateValidatorTest {

    @Autowired
    DateValidator dateValidator;

    private final LocalDateTime invalidLocalDateTime = LocalDateTime.parse("2022-01-01T00:00");
    private final String validLocalDateTime = "2022-08-01T00:00";
    private final String validLocalDate = "2022-08-01";

    @Test
    void validate_shouldThrowException_whenLocalDateTimeIsNotInTheFuture() {
        Assertions.assertThrows(RuntimeException.class, () -> dateValidator.validate(invalidLocalDateTime));
    }

    @Test
    void mapToLocalDateTime_shouldReturnLocalDateTime_whenStringWithTimeIsGiven() {
        LocalDateTime result = dateValidator.mapToLocalDateTime(validLocalDateTime);

        Assertions.assertEquals(result, LocalDateTime.of(2022, 8, 1, 0, 0));
    }

    @Test
    void mapToLocalDateTime_shouldReturnLocalDateTime_whenStringWithTimeIsNotGiven() {
        LocalDateTime result = dateValidator.mapToLocalDateTime(validLocalDate);

        Assertions.assertEquals(result, LocalDateTime.of(2022, 8, 1, 0, 0));
    }

    @Test
    void hasTime_shouldReturnTrue_whenLocalDateTimeStringHasTime() {
        boolean result = dateValidator.hasTime(validLocalDateTime);

        Assertions.assertTrue(result);
    }

    @Test
    void hasTime_shouldReturnFalse_whenLocalDateTimeStringHasNoTime() {
        boolean result = dateValidator.hasTime(validLocalDate);

        Assertions.assertFalse(result);
    }
}